package com.example.poc.stockEtablissement;

import java.util.HashMap;
import java.util.Map;

public enum StockEtablissementEnum {

    ACTIVITEPRINCIPALEETABLISSEMENT("activitePrincipaleEtablissement", 45),
    ACTIVITEPRINCIPALEREGISTREMETIERSETABLISSEMENT("activitePrincipaleRegistreMetiersEtablissement", 7),
    ANNEEEFFECTIFSETABLISSEMENT("anneeEffectifsEtablissement", 6),
    CARACTEREEMPLOYEURETABLISSEMENT("caractereEmployeurEtablissement", 47),
    CODECEDEX2ETABLISSEMENT("codeCedex2Etablissement", 35),
    CODECEDEXETABLISSEMENT("codeCedexEtablissement", 21),
    CODECOMMUNE2ETABLISSEMENT("codeCommune2Etablissement", 34),
    CODECOMMUNEETABLISSEMENT("codeCommuneEtablissement", 20),
    CODEPAYSETRANGER2ETABLISSEMENT("codePaysEtranger2Etablissement", 37),
    CODEPAYSETRANGERETABLISSEMENT("codePaysEtrangerEtablissement", 23),
    CODEPOSTAL2ETABLISSEMENT("codePostal2Etablissement", 30),
    CODEPOSTALETABLISSEMENT("codePostalEtablissement", 16),
    COMPLEMENTADRESSE2ETABLISSEMENT("complementAdresse2Etablissement", 25),
    COMPLEMENTADRESSEETABLISSEMENT("complementAdresseEtablissement", 11),
    DATECREATIONETABLISSEMENT("dateCreationEtablissement", 4),
    DATEDEBUT("dateDebut", 39),
    DATEDERNIERTRAITEMENTETABLISSEMENT("dateDernierTraitementEtablissement", 8),
    DENOMINATIONUSUELLEETABLISSEMENT("denominationUsuelleEtablissement", 44),
    DISTRIBUTIONSPECIALE2ETABLISSEMENT("distributionSpeciale2Etablissement", 33),
    DISTRIBUTIONSPECIALEETABLISSEMENT("distributionSpecialeEtablissement", 19),
    ENSEIGNE1ETABLISSEMENT("enseigne1Etablissement", 41),
    ENSEIGNE2ETABLISSEMENT("enseigne2Etablissement", 42),
    ENSEIGNE3ETABLISSEMENT("enseigne3Etablissement", 43),
    ETABLISSEMENTSIEGE("etablissementSiege", 9),
    ETATADMINISTRATIFETABLISSEMENT("etatAdministratifEtablissement", 40),
    INDICEREPETITION2ETABLISSEMENT("indiceRepetition2Etablissement", 27),
    INDICEREPETITIONETABLISSEMENT("indiceRepetitionEtablissement", 13),
    LIBELLECEDEX2ETABLISSEMENT("libelleCedex2Etablissement", 36),
    LIBELLECEDEXETABLISSEMENT("libelleCedexEtablissement", 22),
    LIBELLECOMMUNE2ETABLISSEMENT("libelleCommune2Etablissement", 31),
    LIBELLECOMMUNEETABLISSEMENT("libelleCommuneEtablissement", 17),
    LIBELLECOMMUNEETRANGER2ETABLISSEMENT("libelleCommuneEtranger2Etablissement", 32),
    LIBELLECOMMUNEETRANGERETABLISSEMENT("libelleCommuneEtrangerEtablissement", 18),
    LIBELLEPAYSETRANGER2ETABLISSEMENT("libellePaysEtranger2Etablissement", 38),
    LIBELLEPAYSETRANGERETABLISSEMENT("libellePaysEtrangerEtablissement", 24),
    LIBELLEVOIE2ETABLISSEMENT("libelleVoie2Etablissement", 29),
    LIBELLEVOIEETABLISSEMENT("libelleVoieEtablissement", 15),
    NIC("nic", 1),
    NOMBREPERIODESETABLISSEMENT("nombrePeriodesEtablissement", 10),
    NOMENCLATUREACTIVITEPRINCIPALEETABLISSEMENT("nomenclatureActivitePrincipaleEtablissement", 46),
    NUMEROVOIE2ETABLISSEMENT("numeroVoie2Etablissement", 26),
    NUMEROVOIEETABLISSEMENT("numeroVoieEtablissement", 12),
    SIREN("siren", 0),
    SIRET("siret", 2),
    STATUTDIFFUSIONETABLISSEMENT("statutDiffusionEtablissement", 3),
    TRANCHEEFFECTIFSETABLISSEMENT("trancheEffectifsEtablissement", 5),
    TYPEVOIE2ETABLISSEMENT("typeVoie2Etablissement", 28),
    TYPEVOIEETABLISSEMENT("typeVoieEtablissement", 14);

    private static final Map<Integer, StockEtablissementEnum> BY_ATOMIC_INDEX = new HashMap<>();
    private static final Map<String, StockEtablissementEnum> BY_HEADER = new HashMap<>();

    static {
        for (StockEtablissementEnum e : values()) {
            BY_ATOMIC_INDEX.put(e.atomicIndex, e);
            BY_HEADER.put(e.header, e);
        }
    }

    public final String header;
    public final int atomicIndex;

    StockEtablissementEnum(String header, int atomicIndex) {
        this.header = header;
        this.atomicIndex = atomicIndex;
    }

    public static StockEtablissementEnum valueOfHeader(String header) {
        return BY_HEADER.get(header);
    }

    public static StockEtablissementEnum valueOfAtomicIndex(int index) {
        return BY_ATOMIC_INDEX.get(index);
    }
}
