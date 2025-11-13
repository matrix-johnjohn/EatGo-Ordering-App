// api/request/chat.ts
import { webSocket } from '@kit.NetworkKit';


// 单例：整个应用只有一个 WebSocket 实例
const ws = webSocket.createWebSocket();

export { ws };
