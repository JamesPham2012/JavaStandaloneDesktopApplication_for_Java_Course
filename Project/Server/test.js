var array = ["1","5","3","2"];

array.push("6");
for(let i=0;i<10;i++){
    var temp = Math.floor(Math.random()*5);
    console.log(temp+" is "+array[temp]);
}
