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
  void loadImages(){
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