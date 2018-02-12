class Bee{
  int x,y,i=1,dis=4;
  int moveX=0,moveY=0;
  Bee(int row,int col){
    setBee(row,col);    
  }
  void setBee(int row,int col){
    x=col;y=row;
  }
  void discover(){
    moveX=0;moveY=0;  
   
   if( ((queen.fx-x)*(queen.fx-x) + (queen.fy-y)*(queen.fy-y) <=9) && !queen.isFlower){//判斷花是否在三格內
      moveX=queen.fx-x;moveY=queen.fy-y;}
   else if( (queen.x-x)*(queen.x-x) + (queen.y-y)*(queen.y-y) <= 4){//判斷是否在2格內     
          moveX=queen.px-x;moveY=queen.py-y;}    
   if(queen.y==6 && queen.x>3 && queen.x<9 && y==4)moveX=0;//不讓他有奇怪的移動

    if(queen.saveX!=0 || queen.saveY!=0){
      if(moveX>0){move(1,0);i=3;}
      else if(moveX<0){move(-1,0);i=2;}   
      else if(moveY>0){move(0,1);i=1;}
      else if(moveY<0){move(0,-1);i=0;}               
    } 
  }
  void move(int mx,int my){
   switch(map.data[y+my][x+mx]){
     case 0:      
       break;
     case 1:
       map.data[y+my][x+mx]=5;//讓蜜蜂不重疊
       map.data[y][x]=1;
       x+=mx;y+=my; 
       break;
     case 5:       
       break;
   }
   
  }
  void render(){
   if(queen.x==x && queen.y==y)queen.isAlive=false;
   if(!map.guide && !map.start && (queen.changeMap==0) ) image(file.bee[i],x*40,y*40,40,40);  
  }
}