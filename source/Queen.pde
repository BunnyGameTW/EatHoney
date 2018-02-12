class Queen{
  int x,y,i=0,saveX,saveY,fx,fy,px,py;
  int changeMap=0;
  float moveTime=0.3f * 1000,moveTimer=0.0f;//計時器
  boolean isFlower,isAlive=true,isHoney,beeMove; 
  Queen(){
    setQueen(1,11);
  }
  void setQueen(int qx,int qy){
    x=qx;y=qy;
  }
  void render(){
    if(!map.guide && !map.start && !isFlower)image(file.queen[i],x*40,y*40,40,40);
    if(isFlower)image(file.queenF[i],x*40,y*40,40,40);
    if(isHoney)image(file.win,0,0,520,520);
    if(!isAlive && !map.start)image(file.lose,0,0,520,520);
  }
  void input(){

    if(moveTimer<=0.0f){
      px=x;py=y;
      saveX=0;saveY=0;
      if(keyPressU){i=0;saveY=-1;move(saveX,saveY);}//更簡潔的方法?
      else if(keyPressD){i=1;saveY=1;move(saveX,saveY);}
      else if(keyPressL){i=2;saveX=-1;move(saveX,saveY);}
      else if(keyPressR){i=3;saveX=1;move(saveX,saveY);}
      else if(!keyPressed)moveTimer = 0.0f;
      
      if(keyPressF==1)getFlower(saveX,saveY);
      else if(isFlower)putFlower();
      
      if(changeMap==0){            
        for(int i=0;i<3;i++){
          bee[i].discover();
      }      
    }
  }else moveTimer-=deltaTime;
}
  void putFlower(){    
    if( map.checkData(x+saveX,y+saveY)==1 ){
      map.data[y+saveY][x+saveX]=4;
      fx=x+saveX;fy=y+saveY;
      isFlower=false;
    }
  }
  void getFlower(int mx,int my){
    if(x==0&&mx==-1)mx=0;else if(x==12&&mx==1)mx=0;//避免超出索引值
    if(y==0&&my==-1)my=0;else if(y==12&&my==1)my=0;
    if( map.checkData(x+mx,y+my)==4 ){
      map.data[y+my][x+mx]=1;
      isFlower=true;
    }
  }
  void move(int mx,int my){
    if(x==0&&mx==-1)mx=0;else if(x==12&&mx==1)mx=0;//避免超出索引值
    if(y==0&&my==-1)my=0;else if(y==12&&my==1)my=0;
    switch(map.data[y+my][x+mx]){
    case 0:
      break;
    case 1:
      x+=mx;y+=my;
      break;
    case 2://hole
      x+=mx;
      changeMap++;changeMap%=2;
      map.setMap(changeMap);
      if(changeMap==1){
        setQueen(1,6);       
        for(int i=0;i<3;i++){
          bee[i].setBee(4,i+5);
          bee[i].i=1;
        }
      }
      else setQueen(10,1);
      break;
    case 3://pen
      if ( map.checkData(x+mx*2,y+my*2)==1 ){
        map.data[y+my][x+mx]=1;
        map.data[y+my*2][x+mx*2]=3;
        x+=mx;y+=my;
      }
      break;
    case 4://flower
      x+=mx;y+=my;
      break; 
    case 8://honey      
      if( map.checkData(x+mx,y+my)==8 )isHoney=true;
      x+=mx;y+=my;
      break; 
    }
    for(int i=0;i<3;i++){if(x==bee[i].x && y==bee[i].y)isAlive=false;}
    moveTimer=moveTime;
  }
}