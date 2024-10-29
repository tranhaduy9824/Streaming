const socket = new WebSocket('ws://192.168.1.5:12345'); // Kết nối đến server WebSocket

socket.onopen = () => {
    console.log('Connected to the server');
};

// Đăng ký
document.getElementById('register-button').onclick = () => {
    const username = document.getElementById('new-username').value;
    socket.send(`REGISTER:${username}`);
};

// Đăng nhập
document.getElementById('login-button').onclick = () => {
    const username = document.getElementById('username').value;
    socket.send(`LOGIN:${username}`);
};

// Tạo phòng
document.getElementById('create-room').onclick = () => {
    const roomName = document.getElementById('room-name').value;
    socket.send(`CREATE_ROOM:${roomName}`);
};

// Gửi bình luận
document.getElementById('send-comment').onclick = () => {
    const comment = document.getElementById('comment').value;
    socket.send(`COMMENT:${comment}`);
};

// Nhận dữ liệu từ server
socket.onmessage = (event) => {
    console.log('Message from server:', event.data);
    // Cập nhật danh sách phòng và bình luận ở đây
};

let localStream;
let peerConnection;
const UDP_PORT = 12346; // Cổng UDP để gửi video

// Khởi tạo WebRTC
const startWebRTC = async () => {
    try {
        // Lấy stream video và audio từ thiết bị
        localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        const video = document.getElementById('video');
        video.srcObject = localStream;

        // Tạo peer connection
        peerConnection = new RTCPeerConnection();

        // Thêm các track vào peer connection
        localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

        // Xử lý ICE Candidate
        peerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                socket.send(`ICE_CANDIDATE:${JSON.stringify(event.candidate)}`);
            }
        };

        // Nhận track từ remote peer
        peerConnection.ontrack = (event) => {
            const remoteVideo = document.createElement('video');
            remoteVideo.srcObject = event.streams[0];
            remoteVideo.play();
            document.body.appendChild(remoteVideo);
        };

        // Tạo offer
        const offer = await peerConnection.createOffer();
        await peerConnection.setLocalDescription(offer);
        socket.send(`OFFER:${JSON.stringify(offer)}`);
        
        // Gửi video qua UDP
        sendVideoOverUDP(localStream);
    } catch (error) {
        console.error('Error starting WebRTC:', error);
    }
};

// Gửi video qua UDP
const sendVideoOverUDP = (stream) => {
    const udpSocket = dgram.createSocket('udp4'); // Tạo UDP socket
    const videoTrack = stream.getVideoTracks()[0];
    const reader = new MediaStreamTrackProcessor(videoTrack).readable.getReader();

    const sendNextFrame = async () => {
        const { done, value } = await reader.read();
        if (done) {
            udpSocket.close(); // Đóng socket khi không còn frame
            return;
        }

        const packet = Buffer.from(value); // Chuyển đổi dữ liệu frame thành Buffer
        udpSocket.send(packet, 0, packet.length, UDP_PORT, '192.168.1.5', (err) => {
            if (err) {
                console.error('UDP send error:', err);
            }
        });

        // Gửi frame tiếp theo
        sendNextFrame();
    };

    sendNextFrame(); // Bắt đầu gửi các frame
};

// Gọi hàm startWebRTC khi cần livestream
document.getElementById('start-livestream').onclick = startWebRTC;
