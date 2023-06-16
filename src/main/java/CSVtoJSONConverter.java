import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CSVtoJSONConverter {
    public static void main(String[] args) {
        // Define the file paths for the CSV and JSON files
        String csvFilePath = ""; //Add your own custom file path
        String jsonFilePath = ""; //Add your own custom file path

        try{
            CSVReader reader = new CSVReader(new FileReader(csvFilePath)); // // Creates a CSVReader object to read the CSV file

            List<String[]> rows = reader.readAll(); // Reads all rows from the CSV file into a List

            String[] headers = rows.get(0); // Extracts the headers from the first row

            ObjectMapper objectMapper = new ObjectMapper(); // Creates an instance of Jackson's ObjectMapper class, which is used for converting Java objects to JSON and vice versa.

            FileWriter fileWriter = new FileWriter(jsonFilePath); // Creates a FileWriter for the output JSON file

            for(int i = 1; i < rows.size(); i++){
                //This outer for-loop iterates over each row of the CSV file, starting from index 1 (skipping the header row). rows.size() gives the total number of rows in the CSV file.

                String[] data = rows.get(i); //Retrieves the data (an array of strings) for the current row using the index i. rows.get(i) gets the row at index i from the rows list.

                Map<String, String> rowData = new LinkedHashMap<>(); //Creates a LinkedHashMap to hold the key-value pairs of each row. The LinkedHashMap is used to preserve the order of insertion for mapping CSV headers to their corresponding values in JSON, ensuring alignment between headers and data in the resulting JSON objects.

                for(int j = 0; j < headers.length; j++){
                    //This inner for-loop iterates over each column of the current row.
                    //headers.length gives the total number of headers/columns in the CSV file.

                    rowData.put(headers[j],data[j]); //Maps the header at index j to its corresponding data value at index j in the rowData LinkedHashMap. headers[j] retrieves the header name at index j. data[j] retrieves the corresponding data value for the current header.
                }

                String jsonString = objectMapper.writeValueAsString(rowData);// Converts the rowData map to a JSON string using the objectMapper's writeValueAsString method. This method serializes the rowData map into its JSON representation.
                fileWriter.write(jsonString);// Writes the JSON string to the output file using the fileWriter's write method. This writes the JSON representation of a single row to the file.

                fileWriter.write("\n");// Writes a newline character to the output file using the fileWriter's write method. This ensures that each JSON object is written on a new line in the output file.
            }

            fileWriter.close(); //Closes the fileWriter
            System.out.println("Conversion successfully completed!");

        }catch (Exception e){
            System.out.println("An error occurred: " + e.getMessage()); //Shows custom error message if an error occurs.
        }
    }
}
