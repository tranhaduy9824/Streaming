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

socket.onmessage = (event) => {
    const [type, data] = event.data.split(':');
    switch (type) {
        case 'ROOM_LIST':
            updateRoomList(JSON.parse(data));
            break;
        case 'COMMENT':
            addComment(data);
            break;
        case 'ICE_CANDIDATE':
            peerConnection.addIceCandidate(new RTCIceCandidate(JSON.parse(data)));
            break;
        case 'ANSWER':
            peerConnection.setRemoteDescription(new RTCSessionDescription(JSON.parse(data)));
            break;
        default:
            console.log('Unknown message type:', type);
    }
};

function updateRoomList(rooms) {
    const roomList = document.getElementById('rooms');
    roomList.innerHTML = '';
    rooms.forEach(room => {
        const li = document.createElement('li');
        li.textContent = room.name;
        roomList.appendChild(li);
    });
}

function addComment(comment) {
    const comments = document.getElementById('comments');
    const commentDiv = document.createElement('div');
    commentDiv.textContent = comment;
    comments.appendChild(commentDiv);
}


let localStream;
let peerConnection;
const UDP_PORT = 12346; // Cổng UDP để gửi video

// Khởi tạo WebRTC
const startWebRTC = async () => {
    try {
        localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
        document.getElementById('video').srcObject = localStream;

        peerConnection = new RTCPeerConnection();

        localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

        peerConnection.onicecandidate = (event) => {
            if (event.candidate) {
                socket.send(`ICE_CANDIDATE:${JSON.stringify(event.candidate)}`);
            }
        };

        peerConnection.ontrack = (event) => {
            const remoteVideo = document.createElement('video');
            remoteVideo.srcObject = event.streams[0];
            remoteVideo.autoplay = true;
            document.body.appendChild(remoteVideo);
        };

        const offer = await peerConnection.createOffer();
        await peerConnection.setLocalDescription(offer);
        socket.send(`OFFER:${JSON.stringify(offer)}`);
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
