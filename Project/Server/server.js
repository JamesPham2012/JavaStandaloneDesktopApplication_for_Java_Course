var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);

server.listen(3000,function(){
    console.log("Server is now running");
});

function Player(x,y,id,execute){
    this.x=x;
    this.y=y;
    this.id = id;
    this.execute=execute;
}
var player = [];
var ID = [];
var length = 2;
var count = 0;
var buffer=0;
var id2 = [];

io.on('connection',function(socket){
    console.log("Player Connected: "+socket.id);
    ID.push(socket.id);
    socket.emit('socketId',{id:socket.id});
    socket.on('Ready',function(data){
        console.log(socket.id+"Has pressed Ready");
        if (id2.length==0){id2.push(socket.id)}
        else{
            id2.push(socket.id);
            for(let i=0;i<id2.length-1;i++){
                if(socket.id==id2[i]){
                   id2.pop();
                   break;
                }
            }
        }
        console.log(id2.length);

        if(id2.length==length){
            console.log(true);
            io.emit("Start");

            id2.pop();
            id2.pop();
            console.log(id2.length);
        }
    })
    
    
    
    socket.on('sendRandomBound',function(data){
        buffer++; 
        if(buffer==2){
            result = Math.random() * (data.upper - data.lower) + data.lower;
            io.emit("receiveRandomBound",{rand:result});
            buffer=0;
        }  
    })
    
    socket.on('sendtoServer',function(data){
        socket.broadcast.emit("sendAnotherPositionToClient",data);
        for(var i=0;i<player.length;i++){
            if(player[i].id==data.id){
                player[i].x = data.x;
                player[i].y = data.y;
                player[i].execute = data.execute;
            }
        }
    })
    socket.on('sendTrigger',function(data){
        socket.broadcast.emit("sendAnotherTrigger",data);
    })
    socket.on('disconnect',function(){
        console.log("Player disconnected: "+socket.id);
        socket.broadcast.emit('playerDisconnected', { id: socket.id });
        		for(var i = 0; i < player.length; i++){
        			if(player[i].id == socket.id){
                        player.splice(i, 1);
                    }
                    if(ID[i]==socket.id){
                        ID.fill("",0,2);
                    }
        		}

    })
    player.push(new Player(50,50,socket.id,true));
})

