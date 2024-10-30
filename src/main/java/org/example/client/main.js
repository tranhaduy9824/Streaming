// const socket = new WebSocket('ws://192.168.1.5:12345');
// // const socket = new WebSocket('ws://localhost:12345');

// socket.onopen = () => {
//     console.log('Connected to server');
//     socket.send('Hello Server');
// };

// socket.onclose = () => {
//     console.log('Disconnected from server');
// };

// socket.onerror = (error) => {
//     console.error('WebSocket error:', error);
// };

// // Đăng ký
// document.getElementById('register-button').onclick = () => {
//     const username = document.getElementById('new-username').value;
//     socket.send(`REGISTER:${username}`);
// };

// // Đăng nhập
// document.getElementById('login-button').onclick = () => {
//     const username = document.getElementById('username').value;
//     socket.send(`LOGIN:${username}`);
// };

// // Tạo phòng
// document.getElementById('create-room').onclick = () => {
//     const roomName = document.getElementById('room-name').value;
//     socket.send(`CREATE_ROOM:${roomName}`);
// };

// // Gửi bình luận
// document.getElementById('send-comment').onclick = () => {
//     const comment = document.getElementById('comment').value;
//     socket.send(`COMMENT:${comment}`);
// };

// socket.onmessage = (event) => {
//     const [type, data] = event.data.split(':');
//     switch (type) {
//         case 'ROOM_LIST':
//             updateRoomList(JSON.parse(data));
//             break;
//         case 'COMMENT':
//             addComment(data);
//             break;
//         case 'ICE_CANDIDATE':
//             peerConnection.addIceCandidate(new RTCIceCandidate(JSON.parse(data)));
//             break;
//         case 'ANSWER':
//             peerConnection.setRemoteDescription(new RTCSessionDescription(JSON.parse(data)));
//             break;
//         case 'OFFER':
//             handleOffer(JSON.parse(data));
//             break;
//         default:
//             console.log('Unknown message type:', type);
//     }
// };

// function updateRoomList(rooms) {
//     const roomList = document.getElementById('rooms');
//     roomList.innerHTML = '';
//     rooms.forEach(room => {
//         const li = document.createElement('li');
//         li.textContent = room.name;
//         roomList.appendChild(li);
//     });
// }

// function addComment(comment) {
//     const comments = document.getElementById('comments');
//     const commentDiv = document.createElement('div');
//     commentDiv.textContent = comment;
//     comments.appendChild(commentDiv);
// }

// let localStream;
// let peerConnection;

// // Khởi tạo WebRTC
// const startWebRTC = async () => {
//     try {
//         localStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });
//         document.getElementById('video').srcObject = localStream;

//         peerConnection = new RTCPeerConnection();

//         localStream.getTracks().forEach(track => peerConnection.addTrack(track, localStream));

//         peerConnection.onicecandidate = (event) => {
//             if (event.candidate) {
//                 socket.send(`ICE_CANDIDATE:${JSON.stringify(event.candidate)}`);
//             }
//         };

//         peerConnection.ontrack = (event) => {
//             const remoteVideo = document.createElement('video');
//             remoteVideo.srcObject = event.streams[0];
//             remoteVideo.autoplay = true;
//             document.body.appendChild(remoteVideo);
//         };

//         const offer = await peerConnection.createOffer();
//         await peerConnection.setLocalDescription(offer);
//         socket.send(`OFFER:${JSON.stringify(offer)}`);
//     } catch (error) {
//         console.error('Error starting WebRTC:', error);
//     }
// };

// // Hàm xử lý khi nhận được OFFER
// const handleOffer = async (offer) => {
//     await peerConnection.setRemoteDescription(new RTCSessionDescription(offer));
//     const answer = await peerConnection.createAnswer();
//     await peerConnection.setLocalDescription(answer);
//     socket.send(`ANSWER:${JSON.stringify(answer)}`);
// };

// // Gọi hàm startWebRTC khi cần livestream
// document.getElementById('start-livestream').onclick = startWebRTC;

const dgram = require('dgram');

const serverAddress = '192.168.1.5'; // Địa chỉ server
const serverPort = 12346; // Cổng server

const client = dgram.createSocket('udp4');
const messageInput = document.getElementById('messageInput');
const sendButton = document.getElementById('sendButton');
const messagesDiv = document.getElementById('messages');

// Gửi tin nhắn UDP
function sendUDPMessage(message) {
    const messageBuffer = Buffer.from(message);
    client.send(messageBuffer, serverPort, serverAddress, (err) => {
        if (err) {
            console.error('Error sending message:', err);
        } else {
            console.log(`Sent: ${message}`);
            displayMessage(message, 'sent');
        }
    });
}

// Nhận tin nhắn từ server
client.on('message', (message) => {
    console.log(`Received: ${message}`);
    displayMessage(message.toString(), 'received');
});

// Hiển thị tin nhắn trong giao diện
function displayMessage(message, type) {
    const messageElement = document.createElement('div');
    messageElement.className = `message ${type}`;
    messageElement.textContent = message;
    messagesDiv.appendChild(messageElement);
    messagesDiv.scrollTop = messagesDiv.scrollHeight; // Cuộn đến cuối
}

// Gửi tin nhắn khi nhấn nút
sendButton.addEventListener('click', () => {
    const input = messageInput.value;
    if (input.startsWith("/register ")) {
        const username = input.split(" ")[1];
        sendUDPMessage(`REGISTER:${username}`);
    } else {
        sendUDPMessage(`COMMENT:${input}`);
    }
    messageInput.value = ''; // Xóa ô nhập sau khi gửi
});

// Thông báo khi client đã sẵn sàng
console.log('Client UDP đã sẵn sàng. Nhập /register <username> để đăng ký hoặc nhập tin nhắn để gửi.');
