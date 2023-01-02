
package app.Services;

import java.awt.Label;
import java.util.Arrays;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Operations {
    
    private  int col, row;  //dimentions
    private  double tempMatrix[][];
    private  JTextField inputField[][];
    private  int result;
    private  JPanel choosePanel [] = new JPanel[8];
    private  int lastCol, lastRow;
    
    // Matrices of circuit
    private  double[][] b;
    private  double[][] zb;
    private  double[][] eb;
    private  double[][] ib;
    

    public Operations() {
        col = row = 0;
    }

    public  double[][] getB() {
        return b;
    }

    public  void setB(double[][] b) {
        this.b = b;
    }

    public  double[][] getZb() {
        return zb;
    }

    public  void setZb(double[][] zb) {
        this.zb = zb;
    }

    public  double[][] getEb() {
        return eb;
    }

    public  void setEb(double[][] eb) {
        this.eb = eb;
    }

    public  double[][] getIb() {
        return ib;
    }

    public  void setIb(double[][] ib) {
        this.ib = ib;
    }
    
    
    
    
     //prompting for matrix's dimensions
     public  void getDimension(double[][] matrix, JCheckBox box) {
         double[][] Copymatrix = matrix;
         
      box.setSelected(false);
      JTextField lField = new JTextField(5); //lenght field 
      JTextField wField = new JTextField(5); //col field
      
      //design input line
      JPanel choosePanel [] = new JPanel [2];
      choosePanel [0] = new JPanel();
      choosePanel [1] = new JPanel();
      choosePanel[0].add(new JLabel("Enter Dimensitions") );
      choosePanel[1].add(new JLabel("Rows:"));
      choosePanel[1].add(lField);
      choosePanel[1].add(Box.createHorizontalStrut(15)); // a spacer
      choosePanel[1].add(new JLabel("Cols:"));
      choosePanel[1].add(wField);
        
      result = JOptionPane.showConfirmDialog(null, choosePanel, 
               null,JOptionPane.OK_CANCEL_OPTION, 
               JOptionPane.PLAIN_MESSAGE);
        
      
      //ok option
       if(result == 0)
       {
                 lastCol = col;
                lastRow = row;
         
         if(wField.getText().equals(""))
             col = 0;
         else
         {
             if(isInt(wField.getText()))
             {
                 col = Integer.parseInt(wField.getText());
             }
             else
             {
                 JOptionPane.showMessageDialog(null, "Wrong Dimensions");
                 col = lastCol;
                 row = lastRow;
                 return;
             }
            
             if(isInt(lField.getText()))
             {
                 row = Integer.parseInt(lField.getText());
             }
             else
             {
                 JOptionPane.showMessageDialog(null, "Wrong Dimensions");
                 col = lastCol;
                 row = lastRow;
                 return;
             }
          
         }
       if(col < 1 || row < 1)
       {
           JOptionPane.showConfirmDialog(null, "You entered wrong dimensions", 
                   "Error",JOptionPane.PLAIN_MESSAGE);
           col  = lastCol;
           row = lastRow;
       }
       else
       {
           tempMatrix = matrix;
           matrix = new double [row][col];
           if(Arrays.equals(Copymatrix, b)) b = matrix;
           else if(Arrays.equals(Copymatrix, zb)) zb = matrix;
           else if(Arrays.equals(Copymatrix, eb)) eb = matrix;
           else if(Arrays.equals(Copymatrix, ib)) ib = matrix;
           
           
           
           if(!setElements(matrix, "Fill your new matrix")) //filling the new matrix
            {
                //backup
                matrix = tempMatrix;
            }
       }
       box.setSelected(true);
       }
       else if(result == 1)
       {
           col = lastCol;
           row = lastRow;
       }
     }//end get Dimension
     
     
    
     //setting a matrix's elementis
    private  boolean setElements(double matrix [][], String title)
    {
        int temp, temp1;             //temprature variable
        String tempString;
        
       JPanel choosePanel [] = new JPanel [row+2];
       choosePanel[0] = new JPanel();
       choosePanel[0].add(new Label(title));
       choosePanel[choosePanel.length-1] = new JPanel();
       choosePanel[choosePanel.length-1].add(new Label("consider space field as zeros"));
       inputField  = new JTextField [matrix.length][matrix[0].length];
        
       
       //lenght loop
       for(temp = 1; temp <= matrix.length; temp++)
       {
           choosePanel[temp] = new JPanel();
           
           
           for(temp1 = 0; temp1 < matrix[0].length; temp1++)
           {
               inputField [temp-1][temp1] = new JTextField(3);
               choosePanel[temp].add(inputField [temp-1][temp1]);
               
               if(temp1 < matrix[0].length -1)
               {
               choosePanel[temp].add(Box.createHorizontalStrut(15)); // a spacer
               }
               
           }//end col loop
           
       }//end row loop
       
       result = JOptionPane.showConfirmDialog(null, choosePanel, 
               null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE);
     
      
      if(result == 0)
      {
          checkTextField(inputField);
       for(temp = 0; temp < matrix.length; temp++)
       {
        for(temp1 = 0; temp1 < matrix[0].length; temp1++)
            {
                tempString = inputField[temp][temp1].getText();
                
                
                 if(isDouble(tempString))
                {
                matrix [temp][temp1] = Double.parseDouble(inputField[temp][temp1].getText());
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "You entered wrong elements");
                    
                    //backup
                    col = lastCol;
                    row = lastRow;
                    return false;
                }                      
            }
       }
       
       return true;
       
    }
      else{
          return false;
      }
      
    }//end get Inputs
    
    
    //for setting spaced fields as zeros
     private  void checkTextField (JTextField field [][] )
     {
         for(int temp = 0; temp < field.length; temp++)
         {
             for(int temp1 = 0; temp1 < field[0].length; temp1++)
             {
                 if(field[temp][temp1].getText().equals(""))
                 field[temp][temp1].setText("0");
             }
         }
     }//end reset
     
    
   
   
    public  void showMatrix(double [][] matrix, String title) {
        
        int temp, temp1; 
        
        JPanel choosePanel [] = new JPanel [matrix.length+1];
       choosePanel[0] = new JPanel ();
       choosePanel[0].add( new JLabel (title) );
   
       for(temp = 1; temp <= matrix.length; temp++)
       {
           choosePanel[temp] = new JPanel();
           
           
           for(temp1 = 0; temp1 < matrix[0].length; temp1++)
           {
               if(matrix[temp-1][temp1] == -0)
               {
                  matrix[temp-1][temp1] = 0; 
               }
               choosePanel[temp].add(new JLabel(String.format("%.2f", matrix[temp-1][temp1])));
               
               if(temp1 < matrix[0].length -1)
               {
               choosePanel[temp].add(Box.createHorizontalStrut(15)); // a spacer
               }
               
           }//end col loop
           
       }//end row loop
       
    if(col == 0 || row == 0)
    {
        JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
    }
    else
    {
    
    JOptionPane.showMessageDialog(null, choosePanel, null, 
            JOptionPane.PLAIN_MESSAGE, null);
    
    }  
    }//end show Matrix

  public  double[][] matrixPlusMatrix (double[][] myMatrix, double[][] m2){
      
      if(myMatrix.length < 1){
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        }
      else {
          int row = myMatrix.length;
          int col = myMatrix[0].length;
          
           double sum[][] = new double [row][col];

    if(m2.length > 0)
    {

for(int i=0;i<row;i++)
{
for(int j=0;j<col;j++)
{
    sum[i][j]=myMatrix[i][j]+m2[i][j];
}
}
    return sum;
    }
        }//end else checking
      return null;
}//end plus matrix
    
  
  
public  double[][] matrixMinusMatrix (double[][] myMatrix, double[][] m2)
{

    if(myMatrix.length < 1)
        {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        }
        else
        {
     
            int row = myMatrix.length;
            int col = myMatrix[0].length;
    double sub[][] = new double [row][col];
    double temp [][] = new double [row][col];


    if(m2.length > 0)
    {

for(int i=0;i<row;i++)
{
for(int j=0;j<col;j++)
{
    temp[i][j]=(-1*m2[i][j]);
    sub[i][j]=myMatrix[i][j]+temp[i][j];
}
}
    return sub;
    }
        }//end else of checking
    
    return new double[row][col];
}


public   double[][] multiplyByMatrix (double[][] myMatrix, double[][] m2)
{
      double results[][] = null;
      double sum;
      
      if(myMatrix.length < 1)
        {
            JOptionPane.showMessageDialog(null, "You haven't entered any matrix");
        }
        else
        {
            
      if(myMatrix.length > 0 && m2.length > 0)
      {
          results = new double [myMatrix.length][m2[0].length];
      
      for ( int i = 0 ; i < myMatrix.length ; i++ )
         {
            for ( int j = 0 ; j < m2[0].length ; j++ )
            {   
                sum = 0;
               for ( int k = 0 ; k < m2.length ; k++ )
               {
                  sum += myMatrix[i][k]*m2[k][j];
               }
 
               results[i][j] = sum;
             
            }
         }
 
     
      }//elements checking
      }//dimensions checking
     return results;
}

    public static void swap (double [] res1, double [] res2)
    {
        int temp;
        double tempDouble;
        
        for(temp = 0; temp < res1.length;temp++)
        {
            tempDouble = res1[temp];
            res1[temp] = res2[temp];
            res2[temp] = tempDouble;
        }
        
    }
    
 public  double getValue (double [][] matrix){
        int temp, temp1, temp2;
        double coeficient;
        double result = 1;
        int sign = 1;       //1 for positive
                            //-1 for negative
        int zeroCounter ;
        
       double res[][] = new double [matrix.length][matrix[0].length];
       
       //copy matrix
        for(temp = 0; temp < matrix.length; temp++)
        {
            for(temp1 = 0; temp1 < matrix[0].length; temp1++)
            {
                res[temp][temp1] = matrix[temp][temp1];;
            }
            
        }
        
        //rearrange rows
        for(temp = 0; temp < res.length; temp++)
        {
            if(res[temp][temp] != 0)
                continue;
            
            for(temp1 = 1; temp1 < res.length -1 ; temp1++)
            {
                if( res[ (temp1 + temp ) % matrix.length][temp] != 0)
                {       //swapping
                swap(res[temp], res[(temp1 + temp ) % res.length]);    
                sign *= -1;
                break;
                }
           }
        }
            
               
        
        //starting simplifing with gaues method
        for(temp = 1; temp < res.length; temp++)
        {
            for(temp1 = 0; temp1 < temp; temp1++)
            {
                //if required element = 0 || division = 0
                if(res[temp][temp1] == 0 || res[temp1][temp1] == 0)
                    continue;
                else
                {
                    zeroCounter = 0;
                    coeficient = res[temp][temp1]/res[temp1][temp1];
                }
            for(temp2 = 0; temp2 < res.length; temp2++)
            {
                res[temp][temp2] = res[temp][temp2]  
                        - res[temp1][temp2] * coeficient;
                
                if(res[temp][temp2] == 0 )
                    zeroCounter++;
            }
            //over flow zeros
            if(temp < res.length -1 && zeroCounter > temp)
            {
             swap(res[temp], res[temp+1]);
             sign *= -1;
             temp--;
            }
            }
        }
        
        for(temp = 0; temp < res.length; temp++)
        {
            result *= res[temp][temp];
        }
        return result * sign;
     }//end getValue
    
    
    public  double [][] transposing (double [][] matrix)
    {
        double [][] transportMatrix = new double[matrix[0].length][matrix.length];
        int temp1, temp; //termprature variable
        
        for(temp = 0 ; temp < matrix[0].length; temp++)
        {
            for(temp1 = 0; temp1 < matrix.length; temp1++)
            {
                 transportMatrix[temp][temp1]  = 
                         matrix[temp1][temp]; //swap (temp, temp1)
            }
        }
        return transportMatrix;
    }
    
    
   public  boolean isInt (String str)
   {
       int temp;
       if (str.length() == '0')
           return false;
       
       for(temp = 0; temp < str.length();temp++)
       {
           if(str.charAt(temp) != '+' && str.charAt(temp) != '-'
                   && !Character.isDigit(str.charAt(temp)))
           {
               return false;
           }
       }
       return true;
   }
   
    private static boolean isDouble (String str)
   {
       int temp;
       if (str.length() == '0')
           return false;
       
       for(temp = 0; temp < str.length();temp++)
       {
           if(str.charAt(temp) != '+' && str.charAt(temp) != '-'
                   && str.charAt(temp) != '.'
                   && !Character.isDigit(str.charAt(temp))
                   )
           {
               return false;
           }
       }
       return true;
   }
}
