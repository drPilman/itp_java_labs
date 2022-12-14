package fractal;

public class Complex {
	public double x;
	public double y;

	Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	Complex() {
		this(0, 0);
	}

	void mul_self(Complex other) {
		double x, y;
		x = this.x * other.x - this.y * other.y;
		y = this.x * other.y + this.y * other.x;
		this.x = x;
		this.y = y;
	}

	void add_self(Complex other) {
		this.x += other.x;
		this.y += other.y;
	}
	void inv_self() {
		this.y = -this.y;
	}
	void abs_self() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
	}

	double len_squared() {
		return x * x + y * y;
	}
}
