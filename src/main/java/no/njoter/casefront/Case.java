package no.njoter.casefront;

import java.time.LocalDateTime;

public class Case {

    private String beskrivelse;
    private String navn;
    private String tlf;
    private String varenr;
    private String løsning;
    private String ansattNavn;
    private String fullText;
    private LocalDateTime tidspunkt;

    public Case(String beskrivelse,
                String navn,
                String tlf,
                String varenr,
                String løsning,
                String ansattNavn,
                LocalDateTime tidspunkt
    ) {
        this.beskrivelse = beskrivelse;
        this.navn = navn;
        this.tlf = tlf;
        this.varenr = varenr;
        this.løsning = løsning;
        this.ansattNavn = ansattNavn;
        this.tidspunkt = tidspunkt;
        this.fullText = setFullText();
    }

    private String setFullText() {
        StringBuilder sb = new StringBuilder(this.beskrivelse);
        sb.append(navn);
        sb.append(tlf);
        sb.append(varenr);
        sb.append(løsning);
        sb.append(ansattNavn);
        sb.append(tidspunkt);
        return sb.toString();
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public String getNavn() {
        return navn;
    }

    public String getTlf() {
        return tlf;
    }

    public String getVarenr() {
        return varenr;
    }

    public String getLøsning() {
        return løsning;
    }

    public String getAnsattNavn() {
        return ansattNavn;
    }

    public LocalDateTime getTidspunkt() {
        return tidspunkt;
    }
    public String getFullText() {
        return fullText;
    }
}
