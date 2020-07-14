This is a README file!

Name: Sree Vandana Nadipalli
Project 2: phonebill

This Project2 phonebill is an extention of project1. One Additional Option is provided, "-textFile fileName"
using which the user can store the phone calls in a file, unlike project1

So, the usage becomes.
java -jar target/phonebill-Summer2020.jar \
[options] <args>
[options] = [-textFile fileName -print -README] they can appear in any order
but the fileName must be given after -textFile option only.
<args> are in this order
<customerName>  <callerNumber>  <calleeNumber>  <start Date>  <start time>  <end Date>  <end time>

When -README switch is selected, irrespective of any argument or switch given or not,
the program displays the README.txt file describing the functionality of the program and does not perform any other operations.

When -print switch is given, the program checks if the required number of arguments
(information that user enters, as mentioned above) are given by the user or not.
If given, it validates the given arguments for a correct format, then it prints the latest phone call information of the user.

If only "-textFile FileName" along with arguments are given,
then program dumps the contents into a file and does not output anything.

If both -print and -textFile are given,
then the program dumps the phone call information into a file and prints the latest phoneCall on to console.

If the arguments are not given or the validation fails, the program informs the details of error to the user.
The program raises error when, too many or too less arguments are provided by the user.
The program also raises error when fileName is not given after -textFile option

When neither of the options are given but the user provides all the required number of arguments,
the program checks the format of these given arguments, of they have wrong format it informs the error, else it wont display anything.
