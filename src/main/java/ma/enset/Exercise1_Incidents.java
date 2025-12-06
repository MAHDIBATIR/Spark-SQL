package ma.enset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;


public class Exercise1_Incidents {
    
    public static void main(String[] args) {
        SparkSession spark = SparkSession.builder()
                .appName("TP3_Exercise1_Incidents")
                .master("local[*]")
                .getOrCreate();
        
        Dataset<Row> dfIncidents = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("src/main/resources/incidents.csv");
        
        System.out.println("DONNÉES CHARGÉES");
        dfIncidents.show(10);
        System.out.println("\nNombre total d'incidents: " + dfIncidents.count());
        
        dfIncidents.createOrReplaceTempView("incidents");
        
        System.out.println("TASK 1: NOMBRE D'INCIDENTS PAR SERVICE");
        Dataset<Row> resultTask1 = spark.sql(
                "SELECT service, COUNT(*) as nombre_incidents " +
                "FROM incidents " +
                "GROUP BY service " +
                "ORDER BY nombre_incidents DESC"
        );
        resultTask1.show();
        
        System.out.println("TASK 2: LES DEUX ANNÉES AVEC LE PLUS D'INCIDENTS");
        Dataset<Row> resultTask2 = spark.sql(
                "SELECT YEAR(date) as annee, COUNT(*) as nombre_incidents " +
                "FROM incidents " +
                "GROUP BY YEAR(date) " +
                "ORDER BY nombre_incidents DESC " +
                "LIMIT 2"
        );
        resultTask2.show();
        
        spark.stop();
    }
}
