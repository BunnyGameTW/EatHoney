import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class eatHoneyBeta extends PApplet {

boolean keyPressU,keyPressD,keyPressL,keyPressR;
int keyPressF=0;
float startTime,deltaTime;
final int ROW=13,COL=13;
int mapSize=520,charSize=40;

Map map=new Map();
Queen queen=new Queen();
Bee [] bee=new Bee[3];
File file=new File();

public void setup()
{
  startTime=millis();
  
  for(int i=0;i<3;i++)bee[i]=new Bee(4,i+5);
  
  file.loadImages();
}
public void draw()
{
  deltaTime=millis()-startTime;
  startTime=millis();
  
  map.render();  
  for(int i=0;i<3;i++)bee[i].render();
  queen.render();
  queen.input();  
}
public void keyPressed() {
   if(keyCode==UP) keyPressU=true;
   else if(keyCode==DOWN)keyPressD=true;
   else if(keyCode==LEFT)keyPressL=true;
   else if(keyCode==RIGHT)keyPressR=true;
   if (key == 'F' || key == 'f') keyPressF=-keyPressF+1;
}
public void keyReleased(){
  keyPressU=false;
  keyPressD=false;
  keyPressL=false;
  keyPressR=false;
}
public void mouseClicked(){
  if(map.guide){
    map.guide=false;
    queen.setQueen(1,11);    
    for(int i=0;i<3;i++){bee[i].setBee(4,i+5); bee[i].i=1;}
    map.setMap(0);
    queen.fx=0;queen.fy=0;
  }  
  if(map.start){
    map.start=false;
    map.guide=true;    
  }
  
  if(queen.changeMap==1 && !queen.isFlower && mouseX>=480 && mouseY<=40){
    map.setMap(1);
    queen.setQueen(1,6);
  }
  if(!queen.isAlive || queen.isHoney){
   
    map.start=true;  
    queen.isAlive=true;
    queen.isHoney=false;
    queen.isFlower=false;
  }
}
class Bee{
  int x,y,i=1,dis=4;
  int moveX=0,moveY=0;
  Bee(int row,int col){
    setBee(row,col);    
  }
  public void setBee(int row,int col){
    x=col;y=row;
  }
  public void discover(){
    moveX=0;moveY=0;  
   
   if( ((queen.fx-x)*(queen.fx-x) + (queen.fy-y)*(queen.fy-y) <=9) && !queen.isFlower){//\u5224\u65b7\u82b1\u662f\u5426\u5728\u4e09\u683c\u5167
      moveX=queen.fx-x;moveY=queen.fy-y;}
   else if( (queen.x-x)*(queen.x-x) + (queen.y-y)*(queen.y-y) <= 4){//\u5224\u65b7\u662f\u5426\u57282\u683c\u5167     
          moveX=queen.px-x;moveY=queen.py-y;}    
   if(queen.y==6 && queen.x>3 && queen.x<9 && y==4)moveX=0;//\u4e0d\u8b93\u4ed6\u6709\u5947\u602a\u7684\u79fb\u52d5

    if(queen.saveX!=0 || queen.saveY!=0){
      if(moveX>0){move(1,0);i=3;}
      else if(moveX<0){move(-1,0);i=2;}   
      else if(moveY>0){move(0,1);i=1;}
      else if(moveY<0){move(0,-1);i=0;}               
    } 
  }
  public void move(int mx,int my){
   switch(map.data[y+my][x+mx]){
     case 0:      
       break;
     case 1:
       map.data[y+my][x+mx]=5;//\u8b93\u871c\u8702\u4e0d\u91cd\u758a
       map.data[y][x]=1;
       x+=mx;y+=my; 
       break;
     case 5:       
       break;
   }
   
  }
  public void render(){
   if(queen.x==x && queen.y==y)queen.isAlive=false;
   if(!map.guide && !map.start && (queen.changeMap==0) ) image(file.bee[i],x*40,y*40,40,40);  
  }
}
class File{
  PImage [] queen=new PImage[4];
  PImage [] queenF=new PImage[4];
  PImage all;
  PImage [] bee=new PImage[4];
  PImage [] button=new PImage[8];
  PImage refresh;PImage Fr;PImage Fp;
  PImage [] map=new PImage [2];
  PImage start; PImage guide;PImage win; PImage lose;
  PImage honey;PImage pen;PImage flower;PImage hole;
  public void loadImages(){
    all = loadImage("atlas.png");
    
    start=all.get(0,0,mapSize,mapSize);
    lose=all.get(520,0,mapSize,mapSize);
    win=all.get(0,520,mapSize,mapSize);
    
    map[0]=all.get(520,520,mapSize,mapSize);
    map[1]=all.get(0,1040,mapSize,mapSize);
    guide=all.get(520,1040,mapSize,mapSize);
    
    honey=all.get(203,1615,charSize,charSize);
    flower=all.get(250,1615,charSize,charSize);
    pen=all.get(303,1615,charSize,charSize);
    hole=all.get(355,1615,charSize,charSize);
    
    refresh=all.get(3,1749,43,43);
    Fr=all.get(10,1560,80,80);
    Fp=all.get(100,1560,80,80);
    
    for(int i=0;i<4;i++){
      queenF[i] = all.get(612+49*i,1569,charSize,charSize);
      queen[i] = all.get(412+49*i,1569,charSize,charSize);
      bee[i] = all.get(203+49*i,1565,charSize,charSize);
      button[i]=all.get(10+charSize*i,1660,charSize,charSize);
      button[i+4]=all.get(10+charSize*i,1710,charSize,charSize);
    }
   
  }
}
class Map{
  int [][]data=new int[ROW][COL];
  int row,col;  
  boolean start=true,guide=false;
  Map(){
    setMap(0);
  }
  public void setMap(int n){
   switch(n){
   case 0:
     data=new int[][]{{0,0,0,0,0,1,1,1,0,0,0,0,0},//0 wall. 1 road. 2 transpoint. 8 honey. 5 bee. 4 flower.
                      {0,1,1,1,0,1,1,1,0,1,1,2,0},
                      {0,1,0,1,0,1,8,1,0,1,0,0,0},
                      {0,1,0,1,0,0,1,0,0,1,1,1,1},
                      {0,0,0,1,1,1,1,1,1,1,0,0,1},
                      {0,1,1,1,0,0,0,0,0,1,0,0,1},
                      {0,1,0,1,1,1,1,1,1,1,0,0,1},
                      {0,1,0,1,0,0,0,0,0,0,0,0,1},
                      {0,1,0,1,1,1,1,1,1,1,1,1,1},
                      {0,1,1,1,1,1,1,1,1,0,0,0,0},
                      {0,1,1,0,0,0,0,0,1,0,1,1,0},
                      {0,1,1,0,1,0,0,0,1,0,0,1,0},
                      {1,1,0,0,1,1,1,1,1,1,1,1,0}
                     };
     
     break;
   case 1:
     data=new int[][]{{0,0,0,0,0,0,0,0,0,0,0,0,9},//0 wall. 1 road. 2 transpoint. 3 pen. 4 flower.9 button
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,1,1,1,1,0,1,3,1,3,1,1,0},
                      {0,0,3,1,0,0,3,1,3,3,1,0,0},
                      {2,1,3,1,1,1,3,1,3,1,1,4,0},
                      {0,0,3,1,0,0,3,1,3,3,1,0,0},
                      {0,1,1,1,1,0,1,3,1,3,1,1,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                      {0,0,0,0,0,0,0,0,0,0,0,0,0},
                     };
     break;
   }
  }
  public int checkData(int x,int y){
    return data[y][x];
  }
  public void render(){    
    image(file.map[queen.changeMap],0,0,520,520);//map
    
    for(row=0;row<ROW;row++){
      for(col=0;col<COL;col++){
        switch(data[row][col]){
          case 2://hole
            image(file.hole,col*40,row*40,40,40);
            break;
          case 3://pen          
            image(file.pen,col*40,row*40,40,40);
            break;
          case 4://flower
            image(file.flower,col*40,row*40,40,40);           
            break;
          case 8://honey
            image(file.honey,col*40,row*40,40,40);            
            break;
          case 9://button
            image(file.refresh,col*40,row*40,40,40);            
            break;
        }       
      }
    }
    if(guide )image(file.guide,0,0,520,520); 
    else if(start )image(file.start,0,0,520,520); 
   
   
    fill(255);
    noStroke();
    rect(0,520,520,80);
  }
}
class Queen{
  int x,y,i=0,saveX,saveY,fx,fy,px,py;
  int changeMap=0;
  float moveTime=0.3f * 1000,moveTimer=0.0f;//\u8a08\u6642\u5668
  boolean isFlower,isAlive=true,isHoney,beeMove; 
  Queen(){
    setQueen(1,11);
  }
  public void setQueen(int qx,int qy){
    x=qx;y=qy;
  }
  public void render(){
    if(!map.guide && !map.start && !isFlower)image(file.queen[i],x*40,y*40,40,40);
    if(isFlower)image(file.queenF[i],x*40,y*40,40,40);
    if(isHoney)image(file.win,0,0,520,520);
    if(!isAlive && !map.start)image(file.lose,0,0,520,520);
  }
  public void input(){

    if(moveTimer<=0.0f){
      px=x;py=y;
      saveX=0;saveY=0;
      if(keyPressU){i=0;saveY=-1;move(saveX,saveY);}//\u66f4\u7c21\u6f54\u7684\u65b9\u6cd5?
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
  public void putFlower(){    
    if( map.checkData(x+saveX,y+saveY)==1 ){
      map.data[y+saveY][x+saveX]=4;
      fx=x+saveX;fy=y+saveY;
      isFlower=false;
    }
  }
  public void getFlower(int mx,int my){
    if(x==0&&mx==-1)mx=0;else if(x==12&&mx==1)mx=0;//\u907f\u514d\u8d85\u51fa\u7d22\u5f15\u503c
    if(y==0&&my==-1)my=0;else if(y==12&&my==1)my=0;
    if( map.checkData(x+mx,y+my)==4 ){
      map.data[y+my][x+mx]=1;
      isFlower=true;
    }
  }
  public void move(int mx,int my){
    if(x==0&&mx==-1)mx=0;else if(x==12&&mx==1)mx=0;//\u907f\u514d\u8d85\u51fa\u7d22\u5f15\u503c
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
  public void settings() {  size(520,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "eatHoneyBeta" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
