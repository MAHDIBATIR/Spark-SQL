-- TP 3: Spark SQL - Exercise 2
-- Script de création de la base de données DB_HOSPITAL

-- Créer la base de données
BEGIN
   EXECUTE IMMEDIATE 'DROP DATABASE DB_HOSPITAL';
EXCEPTION
   WHEN OTHERS THEN NULL;
END;
/
CREATE DATABASE DB_HOSPITAL;

-- Table PATIENTS
CREATE TABLE PATIENTS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NOM VARCHAR(50) NOT NULL,
    PRENOM VARCHAR(50) NOT NULL,
    DATE_NAISSANCE DATE,
    TELEPHONE VARCHAR(20)
);

-- Table MEDECINS
CREATE TABLE MEDECINS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NOM VARCHAR(50) NOT NULL,
    PRENOM VARCHAR(50) NOT NULL,
    SPECIALITE VARCHAR(50)
);

-- Table CONSULTATIONS
CREATE TABLE CONSULTATIONS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    ID_PATIENT INT NOT NULL,
    ID_MEDECIN INT NOT NULL,
    DATE_CONSULTATION DATE NOT NULL,
    HEURE_CONSULTATION TIME,
    DIAGNOSTIC TEXT,
    FOREIGN KEY (ID_PATIENT) REFERENCES PATIENTS(ID) ON DELETE CASCADE,
    FOREIGN KEY (ID_MEDECIN) REFERENCES MEDECINS(ID) ON DELETE CASCADE
);

-- Insertion de données de test dans PATIENTS
INSERT INTO PATIENTS (NOM, PRENOM, DATE_NAISSANCE, TELEPHONE) VALUES
('Alami', 'Ahmed', '1985-03-15', '0612345678'),
('Bennani', 'Fatima', '1990-07-22', '0623456789'),
('Cherkaoui', 'Mohammed', '1978-11-10', '0634567890'),
('Darif', 'Sara', '1995-05-18', '0645678901'),
('El Amrani', 'Hassan', '1982-09-25', '0656789012'),
('Fassi', 'Laila', '1988-12-03', '0667890123'),
('Ghali', 'Karim', '1992-02-14', '0678901234'),
('Hamidi', 'Nadia', '1987-06-30', '0689012345'),
('Idrissi', 'Youssef', '1980-04-08', '0690123456'),
('Jilali', 'Amina', '1993-08-19', '0601234567');

-- Insertion de données de test dans MEDECINS
INSERT INTO MEDECINS (NOM, PRENOM, SPECIALITE) VALUES
('Tazi', 'Omar', 'Cardiologie'),
('Benjelloun', 'Samira', 'Pédiatrie'),
('Kadiri', 'Rachid', 'Orthopédie'),
('Mansouri', 'Zineb', 'Dermatologie'),
('Ouazzani', 'Mehdi', 'Médecine générale');

-- Insertion de données de test dans CONSULTATIONS
INSERT INTO CONSULTATIONS (ID_PATIENT, ID_MEDECIN, DATE_CONSULTATION, HEURE_CONSULTATION, DIAGNOSTIC) VALUES
(1, 1, '2024-01-15', '09:00:00', 'Hypertension artérielle'),
(2, 2, '2024-01-15', '10:30:00', 'Vaccination de routine'),
(3, 3, '2024-01-16', '11:00:00', 'Fracture du poignet'),
(4, 4, '2024-01-16', '14:00:00', 'Eczéma'),
(5, 5, '2024-01-17', '09:30:00', 'Grippe saisonnière'),
(1, 5, '2024-01-17', '15:00:00', 'Contrôle général'),
(6, 2, '2024-01-18', '10:00:00', 'Consultation pédiatrique'),
(7, 1, '2024-01-18', '11:30:00', 'Douleurs thoraciques'),
(8, 4, '2024-01-19', '09:00:00', 'Acné'),
(9, 3, '2024-01-19', '14:30:00', 'Douleur au genou'),
(10, 5, '2024-01-20', '10:00:00', 'Migraine'),
(2, 2, '2024-01-20', '11:00:00', 'Suivi vaccination'),
(3, 1, '2024-01-22', '09:30:00', 'Contrôle cardiaque'),
(4, 5, '2024-01-22', '14:00:00', 'Rhume'),
(5, 3, '2024-01-23', '10:30:00', 'Entorse cheville'),
(6, 4, '2024-01-23', '15:00:00', 'Allergie cutanée'),
(7, 2, '2024-01-24', '09:00:00', 'Vaccination'),
(8, 1, '2024-01-24', '11:00:00', 'Suivi hypertension'),
(9, 5, '2024-01-25', '10:00:00', 'Consultation générale'),
(10, 3, '2024-01-25', '14:30:00', 'Lombalgie');

-- Vérification des données
SELECT 'PATIENTS' as TABLE_NAME, COUNT(*) as NB_RECORDS FROM PATIENTS
UNION ALL
SELECT 'MEDECINS', COUNT(*) FROM MEDECINS
UNION ALL
SELECT 'CONSULTATIONS', COUNT(*) FROM CONSULTATIONS;
