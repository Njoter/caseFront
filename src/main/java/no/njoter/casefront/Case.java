package no.njoter.casefront;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import no.njoter.casefront.util.DateFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Case {

    private StringProperty beskrivelse = new SimpleStringProperty();
    private StringProperty navn = new SimpleStringProperty();
    private StringProperty tlf = new SimpleStringProperty();
    private StringProperty varenr = new SimpleStringProperty();
    private StringProperty løsning = new SimpleStringProperty();
    private StringProperty ansattNavn = new SimpleStringProperty();
    private String fullText = "";
    private LocalDateTime tidspunkt;

    public Case() {}

    public String getBeskrivelse() {
        if (beskrivelse.get() == null) {
            return "";
        }
        return beskrivelse.get();
    }

    public StringProperty beskrivelseProperty() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse.set(beskrivelse);
    }

    public String getNavn() {
        if (navn.get() == null) {
            return "";
        }
        return navn.get();
    }

    public StringProperty navnProperty() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn.set(navn);
    }

    public String getTlf() {
        if (tlf.get() == null) {
            return "";
        }
        return tlf.get();
    }

    public StringProperty tlfProperty() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf.set(tlf);
    }

    public String getVarenr() {
        if (varenr.get() == null) {
            return "";
        }
        return varenr.get();
    }

    public StringProperty varenrProperty() {
        return varenr;
    }

    public void setVarenr(String varenr) {
        this.varenr.set(varenr);
    }

    public String getLøsning() {
        if (løsning.get() == null) {
            return "";
        }
        return løsning.get();
    }

    public StringProperty løsningProperty() {
        return løsning;
    }

    public void setLøsning(String løsning) {
        this.løsning.set(løsning);
    }

    public String getAnsattNavn() {
        if (ansattNavn.get() == null) {
            return "";
        }
        return ansattNavn.get();
    }

    public StringProperty ansattNavnProperty() {
        return ansattNavn;
    }

    public void setAnsattNavn(String ansattNavn) {
        this.ansattNavn.set(ansattNavn);
    }

    public LocalDateTime getTidspunkt() {
        return tidspunkt;
    }

    public void setTidspunkt(LocalDateTime tidspunkt) {
        this.tidspunkt = tidspunkt;
    }

    public LocalDate getTidspunktConvertedToLocalDate() {
        return LocalDate.from(tidspunkt);
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText() {
        StringBuilder sb = new StringBuilder(getBeskrivelse());
        sb.append(getNavn());
        sb.append(getTlf());
        sb.append(getVarenr());
        sb.append(getLøsning());
        sb.append(getAnsattNavn());
        sb.append(DateFormatter.ddmmyyyyFormat(this.tidspunkt));
        this.fullText = sb.toString();
    }
}
