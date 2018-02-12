class Map{
  int [][]data=new int[ROW][COL];
  int row,col;  
  boolean start=true,guide=false;
  Map(){
    setMap(0);
  }
  void setMap(int n){
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
  int checkData(int x,int y){
    return data[y][x];
  }
  void render(){    
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