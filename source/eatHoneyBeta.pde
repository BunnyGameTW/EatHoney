boolean keyPressU,keyPressD,keyPressL,keyPressR;
int keyPressF=0;
float startTime,deltaTime;
final int ROW=13,COL=13;
int mapSize=520,charSize=40;

Map map=new Map();
Queen queen=new Queen();
Bee [] bee=new Bee[3];
File file=new File();

void setup()
{
  startTime=millis();
  size(520,600);
  for(int i=0;i<3;i++)bee[i]=new Bee(4,i+5);
  
  file.loadImages();
}
void draw()
{
  deltaTime=millis()-startTime;
  startTime=millis();
  
  map.render();  
  for(int i=0;i<3;i++)bee[i].render();
  queen.render();
  queen.input();  
}
void keyPressed() {
   if(keyCode==UP) keyPressU=true;
   else if(keyCode==DOWN)keyPressD=true;
   else if(keyCode==LEFT)keyPressL=true;
   else if(keyCode==RIGHT)keyPressR=true;
   if (key == 'F' || key == 'f') keyPressF=-keyPressF+1;
}
void keyReleased(){
  keyPressU=false;
  keyPressD=false;
  keyPressL=false;
  keyPressR=false;
}
void mouseClicked(){
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