/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ja
 */
public enum Surnames {
    S1("Wójcik"),
    S2("Nowak"),
    S3 ("Dziara"),
    S4("Kaczmarek"),
    S5("Ebola"),
    S6("Mazur"),
    S7("Krawczyk"),
    S8("Adamczyk"),
    S9("Dudek"),
    S10("Zając"),
    S11("Wieczorek"),
    S12("Król"),
    S13("Wróbel"),
    S14("Pawlak"),
    S16("Walczak"),
    S15("Stępień"),
    S17("Michalak"),
    S18("Sikora"),
    S19("Baran"),
    S20("Duda"),
    S21("Szewczyk");
    
    private final String surname;
    
    private Surnames(String surname){
        this.surname = surname;
    }
    
    @Override
    public String toString() {
        return surname;
    }
}
