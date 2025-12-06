package ma.enset;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

/**
 * TP 3: Spark SQL - Exercise 2
 * Traitement des données de l'hôpital via MySQL
 */
public class Exercise2_Hospital {
    
    public static void main(String[] args) {
        // Créer une session Spark
        SparkSession spark = SparkSession.builder()
                .appName("TP3_Exercise2_Hospital")
                .master("local[*]")
                .getOrCreate();
        
        // Configuration de la connexion MySQL
        String url = "jdbc:mysql://localhost:3306/DB_HOSPITAL";
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", ""); // Mettre votre mot de passe MySQL
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        
        // Charger les tables depuis MySQL
        System.out.println("================================================================================");
        System.out.println("CHARGEMENT DES DONNÉES DEPUIS MYSQL");
        System.out.println("================================================================================");
        
        Dataset<Row> dfPatients = spark.read().jdbc(url, "PATIENTS", properties);
        Dataset<Row> dfMedecins = spark.read().jdbc(url, "MEDECINS", properties);
        Dataset<Row> dfConsultations = spark.read().jdbc(url, "CONSULTATIONS", properties);
        
        System.out.println("\nTable PATIENTS:");
        dfPatients.show();
        
        System.out.println("\nTable MEDECINS:");
        dfMedecins.show();
        
        System.out.println("\nTable CONSULTATIONS:");
        dfConsultations.show();
        
        // Créer des vues temporaires pour utiliser Spark SQL
        dfPatients.createOrReplaceTempView("patients");
        dfMedecins.createOrReplaceTempView("medecins");
        dfConsultations.createOrReplaceTempView("consultations");
        
        // Task 1: Afficher le nombre de consultations par jour
        System.out.println("\n================================================================================");
        System.out.println("TASK 1: NOMBRE DE CONSULTATIONS PAR JOUR");
        System.out.println("================================================================================");
        Dataset<Row> resultTask1 = spark.sql(
                "SELECT DATE_CONSULTATION, COUNT(*) as nombre_consultations " +
                "FROM consultations " +
                "GROUP BY DATE_CONSULTATION " +
                "ORDER BY DATE_CONSULTATION"
        );
        resultTask1.show();
        
        // Task 2: Afficher le nombre de consultations par médecin
        System.out.println("\n================================================================================");
        System.out.println("TASK 2: NOMBRE DE CONSULTATIONS PAR MÉDECIN (NOM | PRENOM | NOMBRE DE CONSULTATION)");
        System.out.println("================================================================================");
        Dataset<Row> resultTask2 = spark.sql(
                "SELECT m.NOM, m.PRENOM, COUNT(*) as NOMBRE_DE_CONSULTATION " +
                "FROM consultations c " +
                "JOIN medecins m ON c.ID_MEDECIN = m.ID " +
                "GROUP BY m.NOM, m.PRENOM " +
                "ORDER BY NOMBRE_DE_CONSULTATION DESC"
        );
        resultTask2.show();
        
        // Task 3: Afficher pour chaque médecin, le nombre de patients qu'il a assisté
        System.out.println("\n================================================================================");
        System.out.println("TASK 3: NOMBRE DE PATIENTS ASSISTÉS PAR MÉDECIN");
        System.out.println("================================================================================");
        Dataset<Row> resultTask3 = spark.sql(
                "SELECT m.NOM, m.PRENOM, COUNT(DISTINCT c.ID_PATIENT) as nombre_patients " +
                "FROM consultations c " +
                "JOIN medecins m ON c.ID_MEDECIN = m.ID " +
                "GROUP BY m.NOM, m.PRENOM " +
                "ORDER BY nombre_patients DESC"
        );
        resultTask3.show();
        
        // Arrêter la session Spark
        spark.stop();
    }
}
