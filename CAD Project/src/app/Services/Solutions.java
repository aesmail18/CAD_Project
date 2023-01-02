
package app.Services;

public class Solutions {
    
     Operations o;
     private double[][] IL;
     private double[][] JL;
     private double[][] VB;
     private double[][] BT;

    public Solutions(Operations o) {
        this.o = o;
    }
    
    
    public double[][] getIL() {
        return IL;
    }

    public void setIL(double[][] IL) {
        this.IL = IL;
    }

    public double[][] getJL() {
        return JL;
    }

    public void setJL(double[][] JL) {
        this.JL = JL;
    }

    public double[][] getVB() {
        return VB;
    }

    public void setVB(double[][] VB) {
        this.VB = VB;
    }
     
     
     public double[][] findCurrentOfLoops() {
           
         // find the result of ZB * BT * B
         
         // Step1: find BT
         BT = o.transposing(o.getB());
         
         // Step2: find ZB * BT
         double[][] step2 = o.multiplyByMatrix(o.getZb(), BT);
         
         // Step3: find ZB * Step2
         double[][] step3 = o.multiplyByMatrix(o.getB(), step2);
         
         // Step4: find B * EB
         double[][] step4 = o.multiplyByMatrix(o.getB(), o.getEb());
         
         // Step5: find B * IB
         double[][] step5 = o.multiplyByMatrix(o.getB(), o.getZb());
         
         double[][] step6 = o.multiplyByMatrix(step5, o.getIb());
         
         // Step6: Step4 - Step5
         double[][] step7 = o.matrixMinusMatrix(step4, step6);
         ////////////////////////////////////////////////////////////////////////////////////////////////////////
         // Now, we have the equation: step3 * IL = step7
         
         this.IL = new double[o.getB().length][1];
         // Step7: find delta of step3[]
         double delta = o.getValue(step3);
         int value;
         double temp[][] = new double[step3.length][step3[0].length];
             int row = 0;
             
         for(int i = 0; i < o.getB().length; i++){
             
             value = 0;
             copy(temp, step3);
             
             
             
             for(int k = 0; k < temp[0].length; k++){
                 temp[k][i] = step7[k][0];
             }
             
             this.IL[row++][0] = o.getValue(temp) / delta;
         }
         return this.IL;
     }
     
     
     public double[][] findCurrentOfBranches(){
         findCurrentOfLoops();
         return o.multiplyByMatrix(BT, IL);
     }
     
    public double[][] findVoltageOfBranches(){
           
           // Step1: find JB + IB
           double[][] step1 = o.matrixPlusMatrix(findCurrentOfBranches(), o.getIb());
        
           // Step2: find ZB * Step1
           double[][] step2 = o.multiplyByMatrix(o.getZb(), step1);
        
           // find Step2 - EB
           
           return o.matrixMinusMatrix(step2, o.getEb());
     }
    
    public void copy(double[][] temp, double[][] step){
                 for (int i = 0; i < step.length; i++) {
             for (int j = 0; j < step[0].length; j++) {
                 temp[i][j] = step[i][j];
                 
             }
         }
    }
    
     
     
     
     
     
     
}
