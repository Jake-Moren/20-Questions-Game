/*
Cole and Jake
5/31/23
lab 6, 20 questions

this class creates the question node to then be used by the question tree class.

the class variables are,
data: the nodes data in a string
yes, the node for the left / yes path
no, the node for the right / no path

the methods are two different constructors so that a node can be constructed with or without
a path, however it always has data.
*/ // imports
public class QuestionNode {

   public String data; // change to private later
   
   public QuestionNode yes; // yes node
   public QuestionNode no; // no node

   public QuestionNode(String data) {
      this(data,null,null);
   } // constructor method
   
   public QuestionNode(String data, QuestionNode yes, QuestionNode no) {
      this.data = data;
      this.yes = yes;
      this.no = no;
   } // constructor method

} // end of QuestionNode class