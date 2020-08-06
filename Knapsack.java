Coin Change

1. Using 1D DP:
    public int coinChange(int[] c, int a) {
        
        int[] DP=new int[a+1];
        Arrays.fill(DP,a+1);
        DP[0]=0;
        for(int i=1;i<=c.length;i++){
            for(int j=1;j<=a;j++){
                int v=c[i-1];
                if(j>=v) DP[j]=Math.min(DP[j],DP[j-v]+1);
                
            }
        }
        if(DP[a]==a+1) return -1;
        return DP[a]; 
     }
     
2. Using 2D DP:

   public int coinChange(int[] c, int a) {
     
         int[][] DP=new int[coins.length+1][amount+1];
        for(int[] A:DP){
            Arrays.fill(A,amount+2);
        }
        DP[0][0]=0;
        for(int i=0;i<coins.length;i++) DP[i+1][0]=0;
        
        for(int i=1;i<=coins.length;i++){
            for(int j=1;j<=amount;j++){
                int v=coins[i-1];
                DP[i][j]=DP[i-1][j];
                if(j>=v){
                    DP[i][j]=Math.min(DP[i][j],DP[i][j-v]+1);
                }
            }
        }
        if(DP[coins.length][amount]==amount+2) return -1;
        return DP[coins.length][amount];
        
    }
-----------------------------------------------------------------
    Perfect Squares
    Problem Statement: Given a positive integer n, find the least number of perfect square numbers (for example, 1, 4, 9, 16, ...) which sum to n.
    
    
    
    1. Using 1D DP:
    public int numSquares(int n) {
        ArrayList<Integer> c=new ArrayList<>();
        int y=1;
        while(y*y<=n){
            if(y*y==n) return 1;
            c.add(y*y);
            y++;
        }
        int[] DP=new int[n+1];
        Arrays.fill(DP,n+10);
        DP[0]=0;
        for(int i=1;i<=c.size();i++){
            for(int j=1;j<=n;j++){
                int v=c.get(i-1);
                if(j>=v) DP[j]=Math.min(DP[j],DP[j-v]+1);
            }
        }
        
        return DP[n];
    }

    2. Using 2D DP:
    public int numSquares(int n) {
        ArrayList<Integer> c=new ArrayList<>();
        int x=1;
        while(x*x<=n){
            if(x*x==n) return 1;
            c.add(x*x);
            x++;
        }
        
        int[][] DP=new int[c.size()+1][n+1];
        for(int[] A:DP) Arrays.fill(A,n+10);
        for(int i=0;i<=c.size();i++) DP[i][0]=0;
        DP[0][0]=0;
        for(int i=1;i<=c.size();i++){
            for(int j=1;j<=n;j++){
                int v=c.get(i-1);
                DP[i][j]=DP[i-1][j];
                if(j>=v){
                    DP[i][j]=Math.min(DP[i][j],DP[i][j-v]+1);
                }
            }
        }
        return DP[c.size()][n];
    }

------------------------------------------------------------------------------------------------
    Last Stone Weight II
    Problem Statement: We have a collection of rocks, each rock has a positive integer weight.
        Each turn, we choose any two rocks and smash them together.  Suppose the stones have weights x and y with x <= y.  The result of this smash i
        If x == y, both stones are totally destroyed;
        If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
        At the end, there is at most 1 stone left.  Return the smallest possible weight of this stone (the weight is 0 if there are no stones left.)
            
            
            
    Solution: The final rock would be a summation of all the values with giving one set of values positive sign and
    the other set of values the negative sign. The goal is to minimise the weight (magnitude) of the final rock. The problem,
    breaks down into finding two sets (paritioning the stones array) which have the smallest difference.
    Therefore, this problem can be reformulated into a 0-1 Knapsack problem, where the value (weights) of the rocks
    is maximised and their total weight (value) does not exceed math.floor(sum of all stones/2).
        
     
    Solution using 2D DP:

        public int lastStoneWeightII{int[] stones){
            int sum=IntStream.of(stones).sum();
            //sum holds the total sum of the stones array
            
            boolean[][] DP=new boolean[stones.length+1][(sum/2)+1];
            //rows as stones, columns as sum
            //DP[i][j] means picking i elements (inclusive) can the sum 'j' be formed?
            // If yes, then DP[i][j] is true
            // Otherwise, DP[i][j] is false
            
            
            DP[0][0]=true;
            //You can form zero sum using zero elements.
            
            for(int i=1;i<=stones.length;i++){
                DP[i][0]=DP[i-1][0];
                //You can form zero sum NOT USING your ith element
            }
            
            int MaxFound=0;
            //The actual answer would be sum-(2*MaxFound)
            //Because let us assume your sum is 23 then you cannot divide it into two perfectly
            //But you can have a set with sum as 11 or something lower.
            
            
            for(int i = 1 ; i <= stones.length ; i++ ){
                
                for(int j=1 ; j<=sum/2 ; j++){
                    
                    int value = stones[i-1]; //weight and value are same, i-1 because indexing...
                    
                    if(j >= value ){
                        
                        DP[i][j]=DP[i-1][j-v];
                        // if ith element is chosen to be in the set when the max sum is j, then
                        // to accomodate v in the set, borrow the bool value from DP[i-1][j-v]
                        
                        if(DP[i][j]==true){
                            MaxFound=Math.max(MaxFound,j);
                            //Update the max if sum j can be reached.
                        }
                        DP[i][j] | = DP[i-1][j]; 
                        // If ith element is not chosen to be in the set
                        
                        
                    }
                }
            }
            
            return sum - (2*MaxFound);
            
        }
                                     
                                     
    Solution Using 1D DP version 1: We use two rows of length sum/2
        public int lastStoneWeightII(int[] stones){
            int sum=IntStream.of(stones).sum();
            
            boolean[] DP=new boolean[1+(sum/2)];
            //Just one row, this time.
            
            DP[0]=true;
            //It is possible to get sum = '0' using 0 elements.
            
            int MaxFound=0;
            
            for ( int i = 1 ; i < = stones.length ; i++ ){
                int value=stones[i-1];
                
                boolean[] DP2=new boolean[1+(sum/2)];
                //Why a second row?
                //This is a 0-1 Knapsack problem, so every element can
                //only be collected once. Unlike the coin change problem.
                
                for( int j = sum/2  ; j > = 0 ; j -- ){
                    //But why traverse the opposite way
                    //Because let us say that at a certain value of j
                    //DP[j] is true, because the previous i-1 th elements
                    //were able to build a sum equals to j
                    //So, now when the ith element is chosen to be and
                    //calculation for sum J is done, then if j+ value of ith element
                    //equals J then, DP[J] shall be set to True.
                    // A loop from j=0 to sum/2 can also work.
                    
                    if( j - value > = 0 ){
                        // i th element can be included in the set
                        DP2[j]  =  DP[j-value];
                        //Here j behaves as J and j-v as j
                        //Because it is a fact that if v is added to j-v then
                        //output is j and if DP[j-v] was true then DP[j] should be
                        //true as well.
                        
                       
                    }
                    
                    DP2[j] | = DP[j-value];
                    if(DP2[j]){
                            MaxFound=Math.max(MaxFound,j);
                             //This if condition can actuall come before the above line.
                     }
                    //If ith element cannot be included or if you choose not to do so.
                }
                DP=DP2;
                // Updating the old DP by the new DP
                // Now the old DP has the calculations till ith element (inclusive)
            }
        
        return sum - (2*MaxFound) ;
    }
                                     
                    
        
    Solution Using 1D DP version 2: We use a single row of length sum/2
        
        public int lastStoneWeightII(int[] stones){
            
            int sum=IntStream.of(stones).sum();
            
            DP[0]=true;
            int MaxFound=0;
            
        
             for(int i = 1; i < = stones.length ; i ++){
                   
                 int v=stones[i-1];
                 
                 for(int j = sum/2 ; j>=v ; j-- ){
                     
                     //In this case you cannot run a loop from j = 0 to sum/2
                     //You can but then it will be complicated
                     
                     if( j - v >=0 ){
                         DP[j]|=DP[j-v]; 
                         //If DP[j] is already true and if DP[j-v] is false then do not
                         //make DP[j] false
                     }
                     
                     if(DP[j]){
                         MaxFound = Math.max( MaxFound, j );
                     }
                 }
             }
        return sum - (2*MaxFound);
        
    }
                                     
                     
                     

                               
                                     
                  
                                     
                                     
                  
                     
                             
                            
                    
            
            
            
            
            
