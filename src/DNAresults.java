/* Name: Tim He
 * Date: 27/11/2023 (started)
 * Assignment: Analysis of Real Human DNA Files
 * Collaborators: N/A
 *
 * Description:			This program reads in a DNA data file and identifies genetic markers
 *						such as skin type or diabetes risk.
 *
 */
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

public class DNAresults
{
    private String[] rsids;
    private String[] genotypes;

    private String diet;

    private String exercise;
    private int rs4994 = -1;
    private int rs1042713 = -1;
    private int rs1801282 = -1;
    private int rs1042714 = -1;
    private int rs1799883 = -1;

    private int errors = 0;

    private int lineCount = 0;

    // main() method
    // input parameters:	none
    // return value:		none
    //
    // Purpose:				Runs the main program
    private void main() throws IOException
    {
        //WRITE YOUR CODE HERE

        //Ask user for input filename
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name: ");
        String fileName = scanner.nextLine();

        File file = new File(fileName);
        while (!file.exists() || !file.canRead()) {
            System.out.println("File does not exist or cannot be accessed.");
            System.out.println("Enter the file name: ");
            fileName = scanner.nextLine();
            file = new File(fileName);
        }

        System.out.println("Parsing " + fileName);
        //Call parseFile() to open data and store into arrays
        parseFile(fileName);
        //Call checkSkinType() and output message
        System.out.println(checkSkinType(rsids, genotypes));
        //Call checkType2Diabetes() and output message
        System.out.println(checkType2Diabetes(rsids, genotypes));
        //Checking if RSIDs exists
        for (int i = 0;i < rsids.length;i++){
            if (rsids[i].equals("rs4994")){
                rs4994 = i;
            }
            else if (rsids[i].equals("rs1042713")){
                rs1042713 = i;
            }
            else if (rsids[i].equals("rs1801282")){
                rs1801282 = i;
            }
            else if (rsids[i].equals("rs1042714")){
                rs1042714 = i;
            }
            else if (rsids[i].equals("rs1799883")){
                rs1799883 = i;
            }
        }
        //Diet Recommendation Code
        if (rs4994 >= 0 && (genotypes[rs4994].equals("AA") || genotypes[rs4994].equals("TT"))){
            if (rs1042713 >= 0 && (genotypes[rs1042713].equals("AA") || genotypes[rs1042713].equals("TT"))){
                exercise = "[12%] Genetic Privilege: Any Exercise Works For You";
                nextProcess();
            }
            else{
                exercise = "[88%] Genetic Disprivilege: Only High Intensity Exercise Will Help You Lose Weight";
                if (rs1799883 >= 0 && genotypes[rs1799883].equals("GG")){
                    nextProcess();
                }
                else if (rs1801282 >= 0 && genotypes[rs1801282].equals("CC")){
                    diet = "[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet";
                    printDE();
                }
                else {
                    diet = "[45%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Carb Diet";
                    printDE();
                }
            }
        }
        else {
            exercise = "[88%] Genetic Disprivilege: Only High Intensity Exercise Will Help You Lose Weight";
            if (rs1799883 >= 0 && genotypes[rs1799883].equals("GG")){
                nextProcess();
            }
            else if (rs1801282 >= 0 && genotypes[rs1801282].equals("CC")){
                diet = "[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet";
                printDE();
            }
            else {
                diet = "[45%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Carb Diet";
                printDE();
            }
        }
        //Call checkPBCancer() and output message
        System.out.println(checkPBCancer(rsids, genotypes));
        //Call checkStressResilience() and output message
        System.out.println(checkStressResilience(rsids, genotypes));
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("There are " + errors + " data format errors detected within the input file out of " + lineCount + " lines of DNA data.\nThese lines of data have not been analyzed.");
    }

    //Method to remove replication of if processes for diet recommendation
    private void nextProcess(){
        if (rs1801282 >= 0 && genotypes[rs1801282].equals("CC") && rs1042714 >= 0 && genotypes[rs1042714].equals("CC")){
            diet = "[16%] Genetic Privilege: Any Diet Works for You";
        }
        else {
            diet = "[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet";
        }
        printDE();
    }
    //Final method that prints diet and exercise results out
    private void printDE(){
        // Diet and exercise recommendations in relation to their genetic traits created by ChatGPT
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Info on Diet: " + diet);
        if (diet.equals("[16%] Genetic Privilege: Any Diet Works for You")){
            System.out.println("Follow a balanced and varied diet that includes a mix of whole grains, lean proteins, fruits, vegetables, and healthy fats.\n" +
                    "Pay attention to portion sizes and listen to your body's hunger and fullness cues.\n" +
                    "Stay hydrated and limit the intake of processed foods and sugary beverages.");
        }
        else if (diet.equals("[39%] Genetic Disprivilege: You Will Lose 2.5x As Much Weight on a Low Fat Diet")){
            System.out.println("Follow a low-fat diet that emphasizes lean proteins, whole grains, fruits, vegetables, and low-fat dairy products.\n" +
                    "Reduce or avoid saturated and trans fats found in fried foods, processed snacks, and fatty meats.\n" +
                    "Monitor your fat intake and be mindful of portion sizes.");
        }
        else{
            System.out.println("Consider a low-carbohydrate diet that focuses on lean proteins, non-starchy vegetables, healthy fats, and moderate amounts of complex carbohydrates from sources like whole grains, legumes, and fruits.\n" +
                    "Limit or avoid refined carbohydrates, sugary foods, and beverages.\n" +
                    "Monitor your carbohydrate intake and adjust it based on your body's response and energy levels.");
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Info on Exercise: " + exercise);
        if (exercise.equals("[12%] Genetic Privilege: Any Exercise Works For You")){
            System.out.println("Engage in a variety of exercises that you enjoy, such as walking, jogging, cycling, swimming, dancing, or participating in sports.\n" +
                    "Aim for a balanced routine that includes cardiovascular exercises, strength training, and flexibility exercises.\n" +
                    "Consider incorporating activities like yoga or Pilates for overall fitness and stress reduction.");
        }
        else {
            System.out.println("Focus on high-intensity interval training (HIIT) workouts, which involve short bursts of intense exercise followed by brief recovery periods.\n" +
                    "Include exercises like sprints, jump squats, burpees, mountain climbers, or high-intensity cardio exercises.\n" +
                    "Combine HIIT workouts with strength training exercises to build lean muscle mass and boost metabolism.");
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    // parseFile() method
    // input parameters:	filename (name of data file to be read)
    // return value:		none
    //
    // Purpose:				reads file and fills in the rsids[] and genotypes[] arrays
    private void parseFile(String filename) throws IOException
    {
        //WRITE YOUR CODE HERE

        File dnaFile = new File(filename);
        String[] lines = new String(Files.readAllBytes(dnaFile.toPath())).split("\n");
        int count = 0;
        for (String i: lines){
            if (i.charAt(0) != '#'){
                count++;
            }
        }
        String[] rsID = new String[count];
        String[] genotype = new String[count];
        int index = 0;
        for (int i = 0;i < lines.length;i++){
            if (lines[i].charAt(0) != '#'){
                rsID[index] = lines[i].split("\t")[0];
                genotype[index] = lines[i].split("\t")[3];
                index++;
            }
        }
        lineCount = count;
        rsids = rsID;
        genotypes = genotype;
        for (int i = 0;i < rsids.length;i++)
            if ((rsids[i].charAt(0) != 'r') || (rsids[i].charAt(1) != 's') || !Character.isDigit(rsids[i].charAt(rsids[i].length() - 1))) {
               errors++;
            }
    }

    // checkSkinType() method
    // input parameters:	rsids array, genotype array
    // return value:		String
    //
    // Purpose:				scans the rsids and genotypes arrays to find skin type
    //							AA 	Probably light-skinned, European ancestry
    //							AG 	Probably mixed African/European ancestry
    //							GG 	Probably darker-skinned, Asian or African ancestry
    //							** 	No DNA info on Skin Type
    private String checkSkinType(String[] rsIDs, String[] genotypes)
    {
        //WRITE YOUR CODE HERE
        int index = -1;
        for (int i = 0;i < rsIDs.length;i++){
            if (rsIDs[i].equals("rs1426654")){
                index = i;
                break;
            }
        }
        if (index == -1){
            return "No DNA info on Skin Type";
        }
        if (index >= 0 && index <= genotypes.length){
            if (genotypes[index].equals("AA")){
                return "Probably light-skinned, European ancestry";
            }
            else if (genotypes[index].equals("AG")){
                return "Probably mixed African/European ancestry";
            }
            else if (genotypes[index].equals("GG")){
                return "Probably darker-skinned, Asian or African ancestry";
            }
            else return "No DNA info on Skin Type";
        }
        return "No DNA info on Skin Type";
    }

    // checkType2Diabetes
    // input parameters:	rsids array, genotype array
    // return value:		String
    //
    // Purpose:				scans the rsids and genotypes arrays to find diabetes risk
    //							CG	1.3x Increased risk for Type-2 Diabetes
    //							CC	1.3x Increased risk for Type-2 Diabetes
    //							GG	Normal risk for	Type-2	Diabetes
    //							**	No DNA info on Type-2 Diabetes
    private String checkType2Diabetes(String[] rsIDs, String[] genotypes)
    {
        //WRITE YOUR CODE HERE
        int index = -1;
        for (int i = 0;i < rsIDs.length;i++){
            if (rsIDs[i].equals("rs7754840")){
                index = i;
                break;
            }
        }
        if (index == -1){
            return "No DNA info on Type-2 Diabetes";
        }
        if (index >= 0 && index <= genotypes.length){
            if (genotypes[index].equals("CG")){
                return "1.3x Increased risk for Type-2 Diabetes";
            }
            else if (genotypes[index].equals("CC")){
                return "1.3x Increased risk for Type-2 Diabetes";
            }
            else if (genotypes[index].equals("GG")){
                return "Normal risk for Type-2 Diabetes";
            }
            else return "No DNA info on Type-2 Diabetes";
        }
        return "No DNA info on Type-2 Diabetes";
    }

    private String checkPBCancer(String[] rsIDs, String[] genotypes)
    {
        //WRITE YOUR CODE HERE
        int index = -1;
        for (int i = 0;i < rsIDs.length;i++){
            if (rsIDs[i].equals("rs351855")){
                index = i;
                break;
            }
        }
        if (index == -1){
            return "No DNA info on Prostate/Breast Cancer Risk";
        }
        if (index >= 0 && index <= genotypes.length){
            if (genotypes[index].equals("CC")){
                return "Normal Risk for Prostate/Breast Cancer";
            }
            else if (genotypes[index].equals("CT")){
                return "1.2x increased risk for prostate/breast cancer";
            }
            else if (genotypes[index].equals("TT")){
                return "2x increased risk for prostate/breast cancer";
            }
            else return "No DNA info on Prostate/Breast Cancer Risk";
        }
        return "No DNA info on Prostate/Breast Cancer Risk";
    }

    private String checkStressResilience(String[] rsIDs, String[] genotypes)
    {
        //WRITE YOUR CODE HERE
        int index = -1;
        for (int i = 0;i < rsIDs.length;i++){
            if (rsIDs[i].equals("rs4680")){
                index = i;
                break;
            }
        }
        if (index == -1){
            return "No DNA info on Stress Resilience";
        }
        if (index >= 0 && index <= genotypes.length){
            if (genotypes[index].equals("AA")){
                return "Enhanced vulnerability to stress and low pain threshold";
            }
            else if (genotypes[index].equals("AG")){
                return "Normal vulnerability to stress and normal pain threshold";
            }
            else if (genotypes[index].equals("GG")){
                return "Better stress resilience and high pain threshold";
            }
            else return "No DNA info on Stress Resilience";
        }
        return "No DNA info on Stress Resilience";
    }

    // DO NOT CHANGE (just ignore it)
    public static void main(String[] argv) throws IOException
    {
        DNAresults dnaResults = new DNAresults();
        dnaResults.main();
    }
}