/**
 * Write a description of class Horse here.
 * @author Tahrima Akther Milky
 * @version 1
 */

public class Horse
{
    //Fields of class Horse
    private String horseName;
    private char horseSymbol;
    private int distance;
    private boolean horseFallen;
    private double horseConfidence;

    //Constructor of class Horse
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.distance = 0;
        this.horseFallen = false;
        this.horseConfidence = horseConfidence;
        if (horseConfidence < 0)
        {
            horseConfidence = 0;
        }
        else if (horseConfidence > 1)
        {
            horseConfidence = 1;
        }

    }

    //Other methods of class Horse
    public void fall()
    {
        horseFallen = true;
        
    }
    
    public double getConfidence()
    {

        return horseConfidence;
    }
    
    public int getDistanceTravelled()
    {
        return distance;
    }
    
    public String getName()
    {
        return horseName;
    }
    
    public char getSymbol()
    {
        return horseSymbol;
    }
    
    public void goBackToStart()
    {
        distance = 0;
    }
    
    public boolean hasFallen()
    {
        if (horseFallen) {
            return true;
        }

        return false;
    }


    public void moveForward()
    {
        distance += 1;

    }

    public void setConfidence(double newConfidence)
    {
        if (newConfidence < 0)
        {
            System.out.println("Confidence cannot be less than 0");
            horseConfidence = 0;
        }
        else if (newConfidence > 1)
        {
            System.out.println("Confidence cannot be greater than 1");
            horseConfidence = 1;
        }
        else
        {
            horseConfidence = newConfidence;
        }


    }
    
    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;
    }
    
}
