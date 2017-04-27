/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Krzysztof Fabiszak
 */
public enum Companies {
    C1("Płyń S.A."),
    C2("Emirates"),
    C3("Water France"),
    C4("Adria"),
    C5("Alitalia"),
    C6("British Waterways"),
    C7("Belavia"),
    C8("Wasserhansa"),
    C9("KLM"),
    C10("FINNWATER"),
    C11("Austrian"),
    C12("Water Canada"),
    C13("Waterflot");
    
    /**
     * Zmienna przechowująca nazwę firmy
     */
    private final String company;
    
    /**
     * Konstruktor enuma Firm
     * @param company 
     */
    private Companies(String company){
        this.company = company;
    }
    
    @Override
    public String toString() {
        return this.company;
    }
}
