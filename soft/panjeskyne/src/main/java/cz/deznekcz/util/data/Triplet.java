package cz.deznekcz.util.data;

public class Triplet<A,B,C> {

	private A a;
	private B b;
	private C c;
	
	public Triplet() {
		this(null, null, null);
	}
	
	public Triplet(A a, B b, C c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void setA(A a) {
		this.a = a;
	}
	
	public void setB(B b) {
		this.b = b;
	}
	
	public void setC(C c) {
		this.c = c;
	}
	
	public A getA() {
		return a;
	}
	
	public B getB() {
		return b;
	}
	
	public C getC() {
		return c;
	}
}
