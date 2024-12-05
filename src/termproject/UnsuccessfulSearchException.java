/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package termproject;

/**
 *
 * @author Ben
 */
public class UnsuccessfulSearchException extends RuntimeException {

    public UnsuccessfulSearchException() {
        super ("Problem with TwoFourTree");
    }
    public UnsuccessfulSearchException(String errorMsg) {
        super (errorMsg);
    }
    
}
