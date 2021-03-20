import java.util.ArrayList;

class Node
{
    double key; 
    String word;
    ArrayList<String> fileNames = new ArrayList<String>();
    
    int height;
    Node left, right;
    
    //creates a Node with the following information
    Node(double d, String w, String fileName)
    {
        key = d;
        word=w;
        
        fileNames.add(fileName);
        
        height = 1;
    }
    
    //Update fileNames list
    public void addFileName(String fileName){
    	
    	fileNames.add(fileName);
    	
    }
}