package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 * Nelson Vargas
 * RUID: 184-00-3905
 * -->FINAL VERSION LET'S GO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!<--
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		Node first = null;
		Node last = null;
		
	
		while((poly1 != null) && (poly2 != null)){
			
			if(poly1.term.degree < poly2.term.degree) {
				Node addedNode = new Node(poly1.term.coeff, poly1.term.degree, null);
				if(last != null) {
					last.next = addedNode;
					
				} else {
					first = addedNode;
				}
				last = addedNode;
				poly1 = poly1.next;
				
			} else if (poly1.term.degree > poly2.term.degree) {
				Node addedNode = new Node(poly2.term.coeff, poly2.term.degree, null);
				if(last != null) {
					last.next = addedNode;
					
				} else {
					first = addedNode;
				}
				last = addedNode;
				poly2 = poly2.next;
				
			} else if (poly1.term.degree == poly2.term.degree) {
				Node addedNode = new Node((poly1.term.coeff + poly2.term.coeff), poly1.term.degree, null);
				if(last != null) {
					last.next = addedNode;
				} else {
					first = addedNode;
				}
				last = addedNode;
				poly1 = poly1.next;
				poly2 = poly2.next;
				
			}
			if(poly2 == null) {
				while(poly1 != null) {
					Node addNode = new Node(poly1.term.coeff, poly1.term.degree,null);
					last.next = addNode;
					poly1 = poly1.next;
				}
			}
			if(poly1 == null) {
				while(poly2 != null) {
					Node addNode = new Node(poly2.term.coeff, poly2.term.degree,null);
					last.next = addNode;
					poly2 = poly2.next;
				}
			}
		
		}		
		return first;		
	}
			
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node pointer1 = poly1;
		Node pointer2 = poly2;
		Node front = null;
	
		while (pointer1 != null) {
				
				if (front != null) {
					front = add(front, multiplyLL(pointer1,poly2));		
				} else {
					front = multiplyLL(pointer1 , pointer2);
				}
				
			pointer1 = pointer1.next;
		}
		return front;

	}

	
	
	private static Node multiplyLL(Node firstPoly, Node secondPoly) {
		Node first = null;
		Node last = null;
		Node secondPointer = secondPoly;

		
		float firstCoeff = firstPoly.term.coeff;
		int firstDegree = firstPoly.term.degree;
	
		
		while(secondPointer != null) {
			float newCoeff = firstCoeff * secondPointer.term.coeff;
			int newDegree = firstDegree + secondPointer.term.degree;
			Node addedNode = new Node(newCoeff, newDegree, null);
			
			if (last != null) {
				last.next = addedNode;
			} else {
				first = addedNode;
			}
			last = addedNode;
			secondPointer = secondPointer.next;
		}

		return first;
		
	}

	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		/** COMPLETE THIS METHOD **/
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE THIS METHOD COMPILE
		// CHANGE IT AS NEEDED FOR YOUR IMPLEMENTATION
		
		Node ptr = poly;
		float finalSum = 0;
		
		while (ptr != null) {
			finalSum = finalSum + (ptr.term.coeff *(float)Math.pow(x, ptr.term.degree));
			ptr = ptr.next;
		}
		return finalSum;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
