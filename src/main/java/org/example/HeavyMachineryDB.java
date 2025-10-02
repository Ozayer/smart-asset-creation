package org.example;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class HeavyMachineryDB {
    
    private final Map<String, VehicleData> vinDatabase = new HashMap<>();
    
    public HeavyMachineryDB() {
        initializeDatabase();
    }
    
    private void initializeDatabase() {
        // Caterpillar Equipment
        addEquipment("CAT0140HDKB123456", "CATERPILLAR", "140M3", "2023", "Motor Grader", "Diesel V8", "Construction Equipment");
        addEquipment("CAT0320DLME789012", "CATERPILLAR", "320D2L", "2022", "Hydraulic Excavator", "C4.4 ACERT", "Construction Equipment");
        addEquipment("CAT0966MXW345678", "CATERPILLAR", "966M XE", "2024", "Wheel Loader", "C13 ACERT", "Construction Equipment");
        addEquipment("CAT0D6TXLVP901234", "CATERPILLAR", "D6T XL", "2023", "Track-Type Tractor", "C9 ACERT", "Construction Equipment");
        
        // John Deere
        addEquipment("1T0644GXABC123456", "JOHN DEERE", "6120M", "2023", "Utility Tractor", "4.5L 4-Cyl", "Agricultural Equipment");
        addEquipment("1T0310EXDEF789012", "JOHN DEERE", "310L", "2022", "Backhoe Loader", "4.5L 4-Cyl", "Construction Equipment");
        addEquipment("1T08320RXYZ456789", "JOHN DEERE", "8320R", "2024", "Row Crop Tractor", "9.0L 6-Cyl", "Agricultural Equipment");
        addEquipment("1T0544KLMN012345", "JOHN DEERE", "544K", "2023", "Wheel Loader", "6.8L 6-Cyl", "Construction Equipment");
        
        // Komatsu
        addEquipment("KMTPC200LC345678", "KOMATSU", "PC200LC-11", "2023", "Hydraulic Excavator", "SAA4D107E-3", "Construction Equipment");
        addEquipment("KMTD65EX901234", "KOMATSU", "D65EX-18", "2022", "Bulldozer", "SAA6D114E-6", "Construction Equipment");
        addEquipment("KMTWA380567890", "KOMATSU", "WA380-8", "2024", "Wheel Loader", "SAA6D114E-6", "Construction Equipment");
        addEquipment("KMTHM400123456", "KOMATSU", "HM400-5", "2023", "Articulated Dump Truck", "SAA6D125E-7", "Construction Equipment");
        
        // Case Construction
        addEquipment("CNH0580MXYZ123456", "CASE", "580M", "2023", "Backhoe Loader", "4.5L Tier 4", "Construction Equipment");
        addEquipment("CNH0621FABC789012", "CASE", "621F", "2022", "Wheel Loader", "6.7L Tier 4", "Construction Equipment");
        addEquipment("CNH1150MDEF345678", "CASE", "1150M", "2024", "Bulldozer", "6.7L Tier 4", "Construction Equipment");
        addEquipment("CNH0CX245901234", "CASE", "CX245D SR", "2023", "Excavator", "6.7L Tier 4", "Construction Equipment");
        
        // Volvo Construction
        addEquipment("VCE0EC220DDEF123", "VOLVO", "EC220D", "2023", "Crawler Excavator", "D5 Tier 4f", "Construction Equipment");
        addEquipment("VCE0L120GABC456", "VOLVO", "L120G", "2022", "Wheel Loader", "D8 Tier 4f", "Construction Equipment");
        addEquipment("VCE0A40GXYZ789", "VOLVO", "A40G", "2024", "Articulated Hauler", "D13 Tier 4f", "Construction Equipment");
        addEquipment("VCE0DD120CMNO012", "VOLVO", "DD120C", "2023", "Asphalt Compactor", "D5 Tier 4f", "Construction Equipment");
        
        // Incomplete data entries - missing fields to trigger AI suggestions
        addIncompleteEquipment("CAT0349ELXYZ999", "CATERPILLAR", null, null, null, null, null); // Only make
        addIncompleteEquipment("1T09620RABC888", "JOHN DEERE", null, "2023", null, null, null); // Make + year only
        addIncompleteEquipment("KMTPC300777", "KOMATSU", "PC300LC-11", null, null, null, null); // Make + model only
        addIncompleteEquipment("CNH0CX210666", "CASE", null, "2024", "Excavator", null, null); // Make + year + body class
        addIncompleteEquipment("VCE0L220555", "VOLVO", "L220G", "2023", null, null, "Construction Equipment"); // Missing body class + engine
        
        // Bobcat (new manufacturer with missing data)
        addIncompleteEquipment("BOB0S650444", "BOBCAT", null, null, null, null, null); // Only make
        addIncompleteEquipment("BOB0E85333", "BOBCAT", "E85", null, "Compact Excavator", null, null); // Make + model + body class
        
        // Hitachi (another new manufacturer)
        addIncompleteEquipment("HIT0ZX350222", "HITACHI", null, "2022", null, null, null); // Make + year
        addIncompleteEquipment("HIT0ZW310111", "HITACHI", "ZW310-6", null, null, "6-Cyl Diesel", null); // Make + model + engine
        
        // Nissan Heavy Machinery Models
        addIncompleteEquipment("NIS0PE30Y001", "NISSAN", "PE30Y", null, null, null, null);
        addIncompleteEquipment("NIS084ZH1A15U", "NISSAN", "84ZH1A15U", null, null, null, null);
        addIncompleteEquipment("NIS0350Z002", "NISSAN", "350Z", null, null, null, null);
        addIncompleteEquipment("NIS0FRONTIERXE", "NISSAN", "Frontier XE", null, null, null, null);
        addIncompleteEquipment("NIS0PE50Y003", "NISSAN", "PE50Y", null, null, null, null);
        addIncompleteEquipment("NIS0EGH2A30V", "NISSAN", "EGH2A30V", null, null, null, null);
        addIncompleteEquipment("NIS0UD2000004", "NISSAN", "UD2000", null, null, null, null);
        addIncompleteEquipment("NIS0LD20005", "NISSAN", "LD20", null, null, null, null);
        addIncompleteEquipment("NIS0KCUGH2F35PV", "NISSAN", "KCUGH2F35PV", null, null, null, null);
        addIncompleteEquipment("NIS0NV200006", "NISSAN", "NV200", null, null, null, null);
        addIncompleteEquipment("NIS050Y007", "NISSAN", "50Y", null, null, null, null);
        addIncompleteEquipment("NIS0C50KLP008", "NISSAN", "C50KLP", null, null, null, null);
        addIncompleteEquipment("NIS0CABSTAR009", "NISSAN", "Cabstar", null, null, null, null);
        addIncompleteEquipment("NIS0TITAN010", "NISSAN", "Titan", null, null, null, null);
        addIncompleteEquipment("NIS0EH2A25V", "NISSAN", "EH2A25V", null, null, null, null);
        addIncompleteEquipment("NIS0TX40011", "NISSAN", "TX40", null, null, null, null);
        addIncompleteEquipment("NIS060012", "NISSAN", "60", null, null, null, null);
        addIncompleteEquipment("NIS0C80KLP013", "NISSAN", "C80KLP", null, null, null, null);
        addIncompleteEquipment("NIS0TX35014", "NISSAN", "TX35", null, null, null, null);
        addIncompleteEquipment("NIS0KP2A25V", "NISSAN", "KP2A25V", null, null, null, null);
        addIncompleteEquipment("NIS0NV2500HD", "NISSAN", "NV2500HD", null, null, null, null);
        addIncompleteEquipment("NIS0PE40Y015", "NISSAN", "PE40Y", null, null, null, null);
        addIncompleteEquipment("NIS0BX50016", "NISSAN", "BX50", null, null, null, null);
        addIncompleteEquipment("NIS0NJ2M20017", "NISSAN", "NJ2M20", null, null, null, null);
        addIncompleteEquipment("NIS0CABSTARTL35", "NISSAN", "Cabstar TL35", null, null, null, null);
        addIncompleteEquipment("NIS0C15V018", "NISSAN", "C15V", null, null, null, null);
        addIncompleteEquipment("NIS0FG103019", "NISSAN", "FG103", null, null, null, null);
        addIncompleteEquipment("NIS0F38000020", "NISSAN", "F38000", null, null, null, null);
        addIncompleteEquipment("NIS0BGF3A40V", "NISSAN", "BGF3A40V", null, null, null, null);
        addIncompleteEquipment("NIS0UD2300021", "NISSAN", "UD2300", null, null, null, null);
        addIncompleteEquipment("NIS0P90Y022", "NISSAN", "P90Y", null, null, null, null);
        addIncompleteEquipment("NIS0KPH2A25PV", "NISSAN", "KPH2A25PV", null, null, null, null);
        addIncompleteEquipment("NIS0SERSPECV", "NISSAN", "SE-R SPEC V", null, null, null, null);
        addIncompleteEquipment("NIS0BXC50023", "NISSAN", "BXC50", null, null, null, null);
        addIncompleteEquipment("NIS0BX40024", "NISSAN", "BX40", null, null, null, null);
        addIncompleteEquipment("NIS0BF3A35V", "NISSAN", "BF3A35V", null, null, null, null);
        addIncompleteEquipment("NIS0CUGJ02F35PV", "NISSAN", "CUGJ02F35PV", null, null, null, null);
        addIncompleteEquipment("NIS0CPH2A25U", "NISSAN", "CPH2A25U", null, null, null, null);
        addIncompleteEquipment("NIS0H2025", "NISSAN", "H2", null, null, null, null);
        addIncompleteEquipment("NIS0TRADE100", "NISSAN", "Trade 100", null, null, null, null);
        addIncompleteEquipment("NIS0CL60026", "NISSAN", "CL 60", null, null, null, null);
        addIncompleteEquipment("NIS0CRGH2F35PV", "NISSAN", "CRGH2F35PV", null, null, null, null);
        addIncompleteEquipment("NIS0CL30027", "NISSAN", "CL30", null, null, null, null);
        addIncompleteEquipment("NIS0UD3300028", "NISSAN", "UD3300", null, null, null, null);
        addIncompleteEquipment("NIS0PH2A20V", "NISSAN", "PH2A20V", null, null, null, null);
        addIncompleteEquipment("NIS0ATLEON029", "NISSAN", "Atleon", null, null, null, null);
        addIncompleteEquipment("NIS0EGH2A30U", "NISSAN", "EGH2A30U", null, null, null, null);
        addIncompleteEquipment("NIS0NP20030", "NISSAN", "NP20", null, null, null, null);
        addIncompleteEquipment("NIS0BXC30031", "NISSAN", "BXC30", null, null, null, null);
        addIncompleteEquipment("NIS0EGH2032", "NISSAN", "EGH2", null, null, null, null);
        addIncompleteEquipment("NIS0UD2600LP", "NISSAN", "UD2600LP", null, null, null, null);
        addIncompleteEquipment("NIS0QUEST033", "NISSAN", "Quest", null, null, null, null);
        addIncompleteEquipment("NIS0FD30034", "NISSAN", "FD30", null, null, null, null);
        addIncompleteEquipment("NIS0BGFD3A400", "NISSAN", "BGFD3A400", null, null, null, null);
        addIncompleteEquipment("NIS0P50K035", "NISSAN", "P50K", null, null, null, null);
        addIncompleteEquipment("NIS0UB1L15U", "NISSAN", "UB1L15U", null, null, null, null);
        addIncompleteEquipment("NIS0UD1800036", "NISSAN", "UD1800", null, null, null, null);
        addIncompleteEquipment("NIS0TX30037", "NISSAN", "TX30", null, null, null, null);
        addIncompleteEquipment("NIS0UD1200038", "NISSAN", "UD1200", null, null, null, null);
        addIncompleteEquipment("NIS0UD039", "NISSAN", "UD", null, null, null, null);
        addIncompleteEquipment("NIS0RPXT2W2G60NV", "NISSAN", "RPXT2W2G60NV", null, null, null, null);
        addIncompleteEquipment("NIS0NP300040", "NISSAN", "NP300", null, null, null, null);
        addIncompleteEquipment("NIS0BXC40041", "NISSAN", "BXC40", null, null, null, null);
        addIncompleteEquipment("NIS0D21042", "NISSAN", "D21", null, null, null, null);
        addIncompleteEquipment("NIS0NJ1M15043", "NISSAN", "NJ1M15", null, null, null, null);
        addIncompleteEquipment("NIS0UD2600044", "NISSAN", "UD2600", null, null, null, null);
        addIncompleteEquipment("NIS0NI205045", "NISSAN", "NI205", null, null, null, null);
        addIncompleteEquipment("NIS0KPH2A25V2", "NISSAN", "KPH2A25V", null, null, null, null);
        addIncompleteEquipment("NIS0NAVARA046", "NISSAN", "Navara", null, null, null, null);
        addIncompleteEquipment("NIS0NV1500047", "NISSAN", "NV1500", null, null, null, null);
        addIncompleteEquipment("NIS0PD155YPS", "NISSAN", "PD155YPS", null, null, null, null);
        addIncompleteEquipment("NIS0BX30048", "NISSAN", "BX30", null, null, null, null);
        addIncompleteEquipment("NIS0ALTIMA25S", "NISSAN", "Altima 2.5S", null, null, null, null);
        addIncompleteEquipment("NIS0UD3000049", "NISSAN", "UD3000", null, null, null, null);
        addIncompleteEquipment("NIS0CRGH2F35V2", "NISSAN", "CRGH2F35V", null, null, null, null);
        addIncompleteEquipment("NIS0UD1800CS", "NISSAN", "UD1800CS", null, null, null, null);
        addIncompleteEquipment("NIS0P50050", "NISSAN", "P50", null, null, null, null);
        addIncompleteEquipment("NIS0KCPHOA15PV", "NISSAN", "KCPHOA15PV", null, null, null, null);
        addIncompleteEquipment("NIS0UD1400051", "NISSAN", "UD1400", null, null, null, null);
        addIncompleteEquipment("NIS0ARMADA052", "NISSAN", "Armada", null, null, null, null);
        addIncompleteEquipment("NIS0PD8000053", "NISSAN", "PD8000", null, null, null, null);
        
        // Caterpillar Additional Models
        addIncompleteEquipment("CAT0963BLGP001", "CATERPILLAR", "963B LGP", null, null, null, null);
        addIncompleteEquipment("CAT0120MVHP002", "CATERPILLAR", "120M VHP", null, null, null, null);
        addIncompleteEquipment("CAT0D10N003", "CATERPILLAR", "D10N", null, null, null, null);
        addIncompleteEquipment("CAT0345CL004", "CATERPILLAR", "345C L", null, null, null, null);
        addIncompleteEquipment("CAT0TL1255005", "CATERPILLAR", "TL1255", null, null, null, null);
        addIncompleteEquipment("CAT0553C006", "CATERPILLAR", "553C", null, null, null, null);
        addIncompleteEquipment("CAT0627007", "CATERPILLAR", "627", null, null, null, null);
        addIncompleteEquipment("CAT0615CSII008", "CATERPILLAR", "615C Series II", null, null, null, null);
        addIncompleteEquipment("CAT0TH407C009", "CATERPILLAR", "TH407C", null, null, null, null);
        addIncompleteEquipment("CAT0PS200B010", "CATERPILLAR", "PS-200B", null, null, null, null);
        addIncompleteEquipment("CAT0928HZ011", "CATERPILLAR", "928HZ", null, null, null, null);
        addIncompleteEquipment("CAT0335FLCR012", "CATERPILLAR", "335F L CR", null, null, null, null);
        addIncompleteEquipment("CAT0312013", "CATERPILLAR", "312", null, null, null, null);
        addIncompleteEquipment("CAT0PM565014", "CATERPILLAR", "PM565", null, null, null, null);
        addIncompleteEquipment("CAT0D5HLGPSII015", "CATERPILLAR", "D5H LGP Series II", null, null, null, null);
        addIncompleteEquipment("CAT0311B016", "CATERPILLAR", "311B", null, null, null, null);
        addIncompleteEquipment("CAT0D3CSIII017", "CATERPILLAR", "D3C Series III", null, null, null, null);
        addIncompleteEquipment("CAT0225B018", "CATERPILLAR", "225B", null, null, null, null);
        addIncompleteEquipment("CAT0235B019", "CATERPILLAR", "235B", null, null, null, null);
        addIncompleteEquipment("CAT0769B020", "CATERPILLAR", "769B", null, null, null, null);
        addIncompleteEquipment("CAT0318B021", "CATERPILLAR", "318B", null, null, null, null);
        addIncompleteEquipment("CAT0583R022", "CATERPILLAR", "583R", null, null, null, null);
        addIncompleteEquipment("CAT0262023", "CATERPILLAR", "262", null, null, null, null);
        addIncompleteEquipment("CAT0561N024", "CATERPILLAR", "561N", null, null, null, null);
        addIncompleteEquipment("CAT0623F025", "CATERPILLAR", "623F", null, null, null, null);
        addIncompleteEquipment("CAT0TH514026", "CATERPILLAR", "TH514", null, null, null, null);
        addIncompleteEquipment("CAT0966K027", "CATERPILLAR", "966K", null, null, null, null);
        addIncompleteEquipment("CAT0315C028", "CATERPILLAR", "315C", null, null, null, null);
        addIncompleteEquipment("CAT016MVHP029", "CATERPILLAR", "16M VHP", null, null, null, null);
        addIncompleteEquipment("CAT0257B030", "CATERPILLAR", "257B", null, null, null, null);
        addIncompleteEquipment("CAT0308E2CR031", "CATERPILLAR", "308E2 CR", null, null, null, null);
        addIncompleteEquipment("CAT0CP433B032", "CATERPILLAR", "CP-433B", null, null, null, null);
        addIncompleteEquipment("CAT0PL87033", "CATERPILLAR", "PL87", null, null, null, null);
        addIncompleteEquipment("CAT0299C034", "CATERPILLAR", "299C", null, null, null, null);
        addIncompleteEquipment("CAT0D604S035", "CATERPILLAR", "D60-4S", null, null, null, null);
        addIncompleteEquipment("CAT0943036", "CATERPILLAR", "943", null, null, null, null);
        addIncompleteEquipment("CAT0120MVHPP037", "CATERPILLAR", "120M VHP Plus", null, null, null, null);
        addIncompleteEquipment("CAT0966GSII038", "CATERPILLAR", "966G Series II", null, null, null, null);
        addIncompleteEquipment("CAT0432E039", "CATERPILLAR", "432E", null, null, null, null);
        addIncompleteEquipment("CAT0226B3040", "CATERPILLAR", "226B3", null, null, null, null);
        addIncompleteEquipment("CAT0NPV60041", "CATERPILLAR", "NPV60", null, null, null, null);
        addIncompleteEquipment("CAT0D6HSII042", "CATERPILLAR", "D6H Series II", null, null, null, null);
        addIncompleteEquipment("CAT0140G043", "CATERPILLAR", "140G", null, null, null, null);
        addIncompleteEquipment("CAT0587T044", "CATERPILLAR", "587T", null, null, null, null);
        addIncompleteEquipment("CAT0350L045", "CATERPILLAR", "350L", null, null, null, null);
        addIncompleteEquipment("CAT0561B046", "CATERPILLAR", "561B", null, null, null, null);
        addIncompleteEquipment("CAT0904H047", "CATERPILLAR", "904H", null, null, null, null);
        addIncompleteEquipment("CAT0225LC048", "CATERPILLAR", "225 LC", null, null, null, null);
        addIncompleteEquipment("CAT0CP56B049", "CATERPILLAR", "CP56B", null, null, null, null);
        addIncompleteEquipment("CAT0D1SSLGP050", "CATERPILLAR", "D1 SSLGP", null, null, null, null);
        addIncompleteEquipment("CAT0993K051", "CATERPILLAR", "993K", null, null, null, null);
        addIncompleteEquipment("CAT0631052", "CATERPILLAR", "631", null, null, null, null);
        addIncompleteEquipment("CAT0951B053", "CATERPILLAR", "951B", null, null, null, null);
        addIncompleteEquipment("CAT03519371054", "CATERPILLAR", "351-9371", null, null, null, null);
        addIncompleteEquipment("CAT0D2055", "CATERPILLAR", "D2", null, null, null, null);
        addIncompleteEquipment("CAT0262C2056", "CATERPILLAR", "262C2", null, null, null, null);
        addIncompleteEquipment("CAT0262D057", "CATERPILLAR", "262D", null, null, null, null);
        addIncompleteEquipment("CAT0247B3058", "CATERPILLAR", "247B3", null, null, null, null);
        addIncompleteEquipment("CAT0745C059", "CATERPILLAR", "745C", null, null, null, null);
        addIncompleteEquipment("CAT0966H060", "CATERPILLAR", "966H", null, null, null, null);
        addIncompleteEquipment("CAT0AP650B061", "CATERPILLAR", "AP650B", null, null, null, null);
        addIncompleteEquipment("CAT0953LGP062", "CATERPILLAR", "953 LGP", null, null, null, null);
        addIncompleteEquipment("CAT0966D063", "CATERPILLAR", "966D", null, null, null, null);
        addIncompleteEquipment("CAT0D9R064", "CATERPILLAR", "D9R", null, null, null, null);
        addIncompleteEquipment("CAT0446065", "CATERPILLAR", "446", null, null, null, null);
        addIncompleteEquipment("CAT0657E066", "CATERPILLAR", "657E", null, null, null, null);
        addIncompleteEquipment("CAT0H120CS067", "CATERPILLAR", "H120CS", null, null, null, null);
        addIncompleteEquipment("CAT0CB534D068", "CATERPILLAR", "CB-534D", null, null, null, null);
        addIncompleteEquipment("CAT0962G069", "CATERPILLAR", "962G", null, null, null, null);
        addIncompleteEquipment("CAT0980B070", "CATERPILLAR", "980B", null, null, null, null);
        addIncompleteEquipment("CAT0CS563DAW071", "CATERPILLAR", "CS-563D AW", null, null, null, null);
        addIncompleteEquipment("CAT03015072", "CATERPILLAR", "301.5", null, null, null, null);
        addIncompleteEquipment("CAT0834B073", "CATERPILLAR", "834B", null, null, null, null);
        addIncompleteEquipment("CAT0H160ES074", "CATERPILLAR", "H160ES", null, null, null, null);
        addIncompleteEquipment("CAT0D4GLGP075", "CATERPILLAR", "D4G LGP", null, null, null, null);
        addIncompleteEquipment("CAT0986K076", "CATERPILLAR", "986K", null, null, null, null);
        addIncompleteEquipment("CAT0916077", "CATERPILLAR", "916", null, null, null, null);
        addIncompleteEquipment("CAT0TH255C078", "CATERPILLAR", "TH255C", null, null, null, null);
        addIncompleteEquipment("CAT0572E079", "CATERPILLAR", "572E", null, null, null, null);
        addIncompleteEquipment("CAT0980G080", "CATERPILLAR", "980G", null, null, null, null);
        addIncompleteEquipment("CAT0BG650081", "CATERPILLAR", "BG-650", null, null, null, null);
        addIncompleteEquipment("CAT0D2LGP082", "CATERPILLAR", "D2 LGP", null, null, null, null);
        addIncompleteEquipment("CAT0M318C083", "CATERPILLAR", "M318C", null, null, null, null);
        addIncompleteEquipment("CAT0990SII084", "CATERPILLAR", "990 Series II", null, null, null, null);
        addIncompleteEquipment("CAT0931B085", "CATERPILLAR", "931B", null, null, null, null);
        addIncompleteEquipment("CAT0D4086", "CATERPILLAR", "D4", null, null, null, null);
        addIncompleteEquipment("CAT0420FIT087", "CATERPILLAR", "420F IT", null, null, null, null);
        addIncompleteEquipment("CAT0522B088", "CATERPILLAR", "522B", null, null, null, null);
        addIncompleteEquipment("CAT0980K089", "CATERPILLAR", "980K", null, null, null, null);
        addIncompleteEquipment("CAT0924K090", "CATERPILLAR", "924K", null, null, null, null);
        addIncompleteEquipment("CAT0559091", "CATERPILLAR", "559", null, null, null, null);
        addIncompleteEquipment("CAT0D250E092", "CATERPILLAR", "D250E", null, null, null, null);
        addIncompleteEquipment("CAT0EC25KE093", "CATERPILLAR", "EC25KE", null, null, null, null);
        addIncompleteEquipment("CAT0TH417094", "CATERPILLAR", "TH417", null, null, null, null);
        addIncompleteEquipment("CAT0140HVHP095", "CATERPILLAR", "140H VHP", null, null, null, null);
        addIncompleteEquipment("CAT0992K096", "CATERPILLAR", "992K", null, null, null, null);
        addIncompleteEquipment("CAT0TH414097", "CATERPILLAR", "TH414", null, null, null, null);
        addIncompleteEquipment("CAT0XQ204098", "CATERPILLAR", "XQ20-4", null, null, null, null);
        addIncompleteEquipment("CAT0D4CSII099", "CATERPILLAR", "D4C Series II", null, null, null, null);
        addIncompleteEquipment("CAT0225100", "CATERPILLAR", "225", null, null, null, null);
        addIncompleteEquipment("CAT0D350ESII101", "CATERPILLAR", "D350E Series II", null, null, null, null);
        addIncompleteEquipment("CAT0TH255102", "CATERPILLAR", "TH255", null, null, null, null);
        addIncompleteEquipment("CAT0D3CLGPSIII103", "CATERPILLAR", "D3C LGP Series III", null, null, null, null);
        addIncompleteEquipment("CAT0594H104", "CATERPILLAR", "594H", null, null, null, null);
        addIncompleteEquipment("CAT0CB634105", "CATERPILLAR", "CB-634", null, null, null, null);
        addIncompleteEquipment("CAT0D3CXL106", "CATERPILLAR", "D3C XL", null, null, null, null);
        addIncompleteEquipment("CAT0320E107", "CATERPILLAR", "320E", null, null, null, null);
        addIncompleteEquipment("CAT0D350D108", "CATERPILLAR", "D350D", null, null, null, null);
        addIncompleteEquipment("CAT0345C109", "CATERPILLAR", "345C", null, null, null, null);
        addIncompleteEquipment("CAT0AP800C110", "CATERPILLAR", "AP-800C", null, null, null, null);
        addIncompleteEquipment("CAT0CS553111", "CATERPILLAR", "CS-553", null, null, null, null);
        addIncompleteEquipment("CAT0349FL112", "CATERPILLAR", "349F L", null, null, null, null);
        addIncompleteEquipment("CAT0633D113", "CATERPILLAR", "633D", null, null, null, null);
        addIncompleteEquipment("CAT0D8114", "CATERPILLAR", "D8", null, null, null, null);
        addIncompleteEquipment("CAT0E8000115", "CATERPILLAR", "E8000", null, null, null, null);
        addIncompleteEquipment("CAT0XQ20116", "CATERPILLAR", "XQ20", null, null, null, null);
        addIncompleteEquipment("CAT0EP15T117", "CATERPILLAR", "EP15T", null, null, null, null);
        addIncompleteEquipment("CAT0D6TXL118", "CATERPILLAR", "D6T XL", null, null, null, null);
        addIncompleteEquipment("CAT0815119", "CATERPILLAR", "815", null, null, null, null);
        addIncompleteEquipment("CAT0303.5ECR120", "CATERPILLAR", "303.5E CR", null, null, null, null);
        addIncompleteEquipment("CAT0M313D121", "CATERPILLAR", "M313D", null, null, null, null);
        addIncompleteEquipment("CAT0572R122", "CATERPILLAR", "572R", null, null, null, null);
        addIncompleteEquipment("CAT0CB434D123", "CATERPILLAR", "CB-434D", null, null, null, null);
        addIncompleteEquipment("CAT0994D124", "CATERPILLAR", "994D", null, null, null, null);
        addIncompleteEquipment("CAT0D7E125", "CATERPILLAR", "D7E", null, null, null, null);
        addIncompleteEquipment("CAT0416B126", "CATERPILLAR", "416B", null, null, null, null);
        addIncompleteEquipment("CAT0428E127", "CATERPILLAR", "428E", null, null, null, null);
        addIncompleteEquipment("CAT0235C128", "CATERPILLAR", "235C", null, null, null, null);
        addIncompleteEquipment("CAT0XQ60P4129", "CATERPILLAR", "XQ60P4", null, null, null, null);
        addIncompleteEquipment("CAT0236D130", "CATERPILLAR", "236D", null, null, null, null);
        addIncompleteEquipment("CAT0EC30K131", "CATERPILLAR", "EC30K", null, null, null, null);
        addIncompleteEquipment("CAT0980F132", "CATERPILLAR", "980F", null, null, null, null);
        addIncompleteEquipment("CAT0553133", "CATERPILLAR", "553", null, null, null, null);
        addIncompleteEquipment("CAT0835134", "CATERPILLAR", "835", null, null, null, null);
        addIncompleteEquipment("CAT012M2VHPP135", "CATERPILLAR", "12M2 VHP Plus", null, null, null, null);
        addIncompleteEquipment("CAT0CB614136", "CATERPILLAR", "CB-614", null, null, null, null);
        addIncompleteEquipment("CAT0D4HLGP137", "CATERPILLAR", "D4H LGP", null, null, null, null);
        addIncompleteEquipment("CAT03044C138", "CATERPILLAR", "3044C", null, null, null, null);
        addIncompleteEquipment("CAT0239D3139", "CATERPILLAR", "239D3", null, null, null, null);
        addIncompleteEquipment("CAT0313BCR140", "CATERPILLAR", "313B CR", null, null, null, null);
        addIncompleteEquipment("CAT0426CIT141", "CATERPILLAR", "426C IT", null, null, null, null);
        addIncompleteEquipment("CAT0962H142", "CATERPILLAR", "962H", null, null, null, null);
        addIncompleteEquipment("CAT0317143", "CATERPILLAR", "317", null, null, null, null);
        addIncompleteEquipment("CAT0PM565B144", "CATERPILLAR", "PM-565B", null, null, null, null);
        addIncompleteEquipment("CAT0631G145", "CATERPILLAR", "631G", null, null, null, null);
        addIncompleteEquipment("CAT0D10R146", "CATERPILLAR", "D10R", null, null, null, null);
        addIncompleteEquipment("CAT03456147", "CATERPILLAR", "3456", null, null, null, null);
        addIncompleteEquipment("CAT0268B148", "CATERPILLAR", "268B", null, null, null, null);
        addIncompleteEquipment("CAT0574149", "CATERPILLAR", "574", null, null, null, null);
        addIncompleteEquipment("CAT0AP1055D150", "CATERPILLAR", "AP-1055D", null, null, null, null);
        addIncompleteEquipment("CAT0436151", "CATERPILLAR", "436", null, null, null, null);
        addIncompleteEquipment("CAT0420XE152", "CATERPILLAR", "420 XE", null, null, null, null);
        addIncompleteEquipment("CAT0TH417C153", "CATERPILLAR", "TH417C", null, null, null, null);
        addIncompleteEquipment("CAT0621B154", "CATERPILLAR", "621B", null, null, null, null);
        addIncompleteEquipment("CAT0988B155", "CATERPILLAR", "988B", null, null, null, null);
        addIncompleteEquipment("CAT0D6HLGPSII156", "CATERPILLAR", "D6H LGP Series II", null, null, null, null);
        addIncompleteEquipment("CAT0299D2157", "CATERPILLAR", "299D2", null, null, null, null);
        addIncompleteEquipment("CAT0E110B158", "CATERPILLAR", "E110B", null, null, null, null);
        addIncompleteEquipment("CAT0M318159", "CATERPILLAR", "M318", null, null, null, null);
        addIncompleteEquipment("CAT0AP1055F160", "CATERPILLAR", "AP1055F", null, null, null, null);
        addIncompleteEquipment("CAT0325L161", "CATERPILLAR", "325L", null, null, null, null);
        addIncompleteEquipment("CAT0CS563D162", "CATERPILLAR", "CS-563D", null, null, null, null);
        addIncompleteEquipment("CAT0231D163", "CATERPILLAR", "231D", null, null, null, null);
        addIncompleteEquipment("CAT0463164", "CATERPILLAR", "463", null, null, null, null);
        addIncompleteEquipment("CAT0PS150C165", "CATERPILLAR", "PS-150C", null, null, null, null);
        addIncompleteEquipment("CAT03176166", "CATERPILLAR", "3176", null, null, null, null);
        addIncompleteEquipment("CAT0D7ELGP167", "CATERPILLAR", "D7E LGP", null, null, null, null);
        addIncompleteEquipment("CAT0CB434B168", "CATERPILLAR", "CB434B", null, null, null, null);
        addIncompleteEquipment("CAT0CT660169", "CATERPILLAR", "CT660", null, null, null, null);
        addIncompleteEquipment("CAT0235D170", "CATERPILLAR", "235D", null, null, null, null);
        addIncompleteEquipment("CAT0219171", "CATERPILLAR", "219", null, null, null, null);
        addIncompleteEquipment("CAT0825G172", "CATERPILLAR", "825G", null, null, null, null);
        addIncompleteEquipment("CAT0IT38G173", "CATERPILLAR", "IT38G", null, null, null, null);
        addIncompleteEquipment("CAT05230B174", "CATERPILLAR", "5230B", null, null, null, null);
        addIncompleteEquipment("CAT014G175", "CATERPILLAR", "14G", null, null, null, null);
        addIncompleteEquipment("CAT0M50D176", "CATERPILLAR", "M50D", null, null, null, null);
        addIncompleteEquipment("CAT0651B177", "CATERPILLAR", "651B", null, null, null, null);
        addIncompleteEquipment("CAT0D6XE178", "CATERPILLAR", "D6 XE", null, null, null, null);
        addIncompleteEquipment("CAT0988F179", "CATERPILLAR", "988F", null, null, null, null);
        addIncompleteEquipment("CAT0325FL180", "CATERPILLAR", "325F L", null, null, null, null);
        addIncompleteEquipment("CAT0IT12181", "CATERPILLAR", "IT12", null, null, null, null);
        addIncompleteEquipment("CAT012HVHPP182", "CATERPILLAR", "12H VHP Plus", null, null, null, null);
        addIncompleteEquipment("CAT0225DLC183", "CATERPILLAR", "225DLC", null, null, null, null);
        addIncompleteEquipment("CAT0D6RLGPSIII184", "CATERPILLAR", "D6R LGP Series III", null, null, null, null);
        addIncompleteEquipment("CAT0627G185", "CATERPILLAR", "627G", null, null, null, null);
        addIncompleteEquipment("CAT0924HZ186", "CATERPILLAR", "924HZ", null, null, null, null);
        addIncompleteEquipment("CAT0922187", "CATERPILLAR", "922", null, null, null, null);
        addIncompleteEquipment("CAT0D4KLGP188", "CATERPILLAR", "D4K LGP", null, null, null, null);
        addIncompleteEquipment("CAT0D3GXL189", "CATERPILLAR", "D3G XL", null, null, null, null);
        addIncompleteEquipment("CAT0M322D190", "CATERPILLAR", "M322D", null, null, null, null);
        addIncompleteEquipment("CAT0797B191", "CATERPILLAR", "797B", null, null, null, null);
        addIncompleteEquipment("CAT0320192", "CATERPILLAR", "320", null, null, null, null);
        addIncompleteEquipment("CAT0336FL193", "CATERPILLAR", "336F L", null, null, null, null);
        addIncompleteEquipment("CAT0430D194", "CATERPILLAR", "430D", null, null, null, null);
        addIncompleteEquipment("CAT0TL1055195", "CATERPILLAR", "TL1055", null, null, null, null);
    }
    
    private void addEquipment(String vin, String make, String model, String year, 
                             String bodyClass, String engine, String vehicleType) {
        VehicleData data = new VehicleData();
        data.setVin(vin);
        data.setMake(make);
        data.setModel(model);
        data.setYear(year);
        data.setBodyClass(bodyClass);
        data.setEngineSize(engine);
        data.setVehicleType(vehicleType);
        
        vinDatabase.put(vin.toUpperCase(), data);
    }
    
    private void addIncompleteEquipment(String vin, String make, String model, String year, 
                                       String bodyClass, String engine, String vehicleType) {
        VehicleData data = new VehicleData();
        data.setVin(vin);
        if (make != null && !make.isEmpty()) data.setMake(make);
        if (model != null && !model.isEmpty()) data.setModel(model);
        if (year != null && !year.isEmpty()) data.setYear(year);
        if (bodyClass != null && !bodyClass.isEmpty()) data.setBodyClass(bodyClass);
        if (engine != null && !engine.isEmpty()) data.setEngineSize(engine);
        if (vehicleType != null && !vehicleType.isEmpty()) data.setVehicleType(vehicleType);
        
        vinDatabase.put(vin.toUpperCase(), data);
    }
    
    public VehicleData lookupVin(String vin) {
        return vinDatabase.get(vin.toUpperCase());
    }
    
    public boolean hasVin(String vin) {
        return vinDatabase.containsKey(vin.toUpperCase());
    }
    
    public Set<String> getAllVins() {
        return vinDatabase.keySet();
    }
}