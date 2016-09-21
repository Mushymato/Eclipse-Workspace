package cn.math.objects;

import cn.math.objects.PrettyMath;

public class Matrix {
	/**
	 * a double[][] that acts as a matrix
	 */
	private double[][] matrix;

	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * Integer equal to number of rows
	 */
	private int rows;
	/**
	 * Integer equal to number of cols
	 */
	private int cols;

	/**
	 * Initializes a new matrix using width and height
	 * 
	 * @param row
	 *            number of rows of the matrix
	 * @param col
	 *            number of columns of the matrix
	 * @return new double[][]
	 */
	public Matrix(int row, int col) {
		matrix = new double[row][col];
		rows = row;
		cols = col;
	}

	/**
	 * Initializes a new matrix using a 2nd dimension double array
	 * 
	 * @param entries
	 * @param isAug
	 */
	public Matrix(double[][] entries) {
		for (int i = 0; i < entries.length - 1; i++) {
			if (entries[i].length != entries[i + 1].length) {
				System.err.println("Inconsistent width on row " + i + " and " + (i + 1));
				System.exit(0);
			}
		}
		matrix = entries;
		rows = entries.length;
		cols = entries[0].length;
	}

	/**
	 * Create a new identity matrix
	 * 
	 * @param idMatrixSize
	 * @throws Exception
	 */
	public Matrix(int idMatrixSize) throws Exception {
		if (idMatrixSize < 0) {
			throw new Exception("Matrix size must be at least 1x1!");
		}
		matrix = new double[idMatrixSize][idMatrixSize];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i][i] = 1;
		}
		cols = idMatrixSize;
		rows = idMatrixSize;
	}

	/**
	 * Temporary identity matrix for calculations
	 * 
	 * @param idMatrixSize
	 * @return
	 * @throws Exception
	 */
	public static Matrix idMatrix(int idMatrixSize) {
		try {
			return new Matrix(idMatrixSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Prints the matrix
	 * 
	 * @param matrix
	 */
	public void printMatrix() {

		for (int i = 0; i < matrix.length; i++) {
			StringBuilder build = new StringBuilder();
			build.append('[');
			for (int j = 0; j < matrix[i].length; j++) {
				build.append(PrettyMath.toFrac(matrix[i][j]));
				if (j != matrix[i].length - 1) {
					build.append('\t');
				}

			}
			build.append("]");
			System.out.println(build.toString());
		}
	}

	/**
	 * Add a matrix to this matrix
	 * 
	 * @param mat
	 *            matrix to add this matrix to
	 * @return answer matrix or null if operation impossible
	 */
	public Matrix add(Matrix mat) {
		if (this.rows == mat.rows && this.cols == mat.cols) {
			Matrix ans = new Matrix(this.rows, this.cols);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					ans.matrix[i][j] = this.matrix[i][j] + mat.matrix[i][j];
				}
			}
			return ans;
		}
		return null;
	}

	/**
	 * Subtract a matrix from this matrix
	 * 
	 * @param mat
	 *            matrix subtracted from this matrix
	 * @return answer matrix or null if operation impossible
	 */
	public Matrix subtract(Matrix mat) {
		if (this.rows == mat.rows && this.cols == mat.cols) {
			Matrix ans = new Matrix(this.rows, this.cols);
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					ans.matrix[i][j] = this.matrix[i][j] - mat.matrix[i][j];
				}
			}
			return ans;
		}
		return null;
	}

	/**
	 * Multiplies this matrix by a number
	 * 
	 * @param num
	 *            a real number
	 * @return answer matrix
	 */
	public Matrix multiply(double num) {
		Matrix ans = new Matrix(this.rows, this.cols);
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				ans.matrix[i][j] = this.matrix[i][j] * num;
			}
		}
		return ans;
	}

	/**
	 * Multiplies this matrix by a matrix
	 * 
	 * @param mat
	 * @return answer matrix
	 */
	public Matrix multiply(Matrix mat) {
		if (this.cols == mat.rows) {
			Matrix ans = new Matrix(this.rows, mat.cols);

			for (int i = 0; i < ans.matrix.length; i++) {
				for (int j = 0; j < ans.matrix[i].length; j++) {
					double cell = 0;
					for (int j2 = 0; j2 < this.cols; j2++) {
						cell = cell + this.matrix[i][j2] * mat.matrix[j2][j];
					}
					ans.matrix[i][j] = cell;
				}
			}
			return ans;

		}
		return null;
	}

	/**
	 * Transpose this matrix
	 * 
	 * @return this Matrix instance
	 */
	public Matrix transpose() {
		double[][] mat = new double[cols][rows];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				mat[j][i] = matrix[i][j];
			}
		}
		matrix = mat;
		return this;
	}

	private double[] rowAdd(double[] a, double coef, double[] b) {
		if (a.length != b.length) {
			return null;
		}
		double[] ans = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			ans[i] = a[i] + coef * b[i];
		}
		return ans;
	}

	/**
	 * Performs bubbleSort based on number of leading 0s
	 */
	public void rowSort() {
		boolean swapped = true;
		int j = 0;
		double[] tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < matrix.length - j; i++) {
				if (countZeros(matrix[i]) > countZeros(matrix[i + 1])) {
					tmp = matrix[i];
					matrix[i] = matrix[i + 1];
					matrix[i + 1] = tmp;
					swapped = true;
				}
			}
		}
	}

	private int countZeros(double[] row) {
		int count;
		for (count = 0; count < row.length; count++) {
			if (row[count] != 0) {
				break;
			}
		}
		return count;
	}

	/**
	 * Gaussian algorithm for augmented matrix
	 * 
	 * @return answer in RREF
	 */
	public Matrix RREF() {
		Matrix ans = this;
		// i defines # of finished rows
		// j defines current column being reduced.
		int j = 0;
		for (int i = 0; i < ans.rows && j < ans.cols - 1; i++, j++) {
			ans.rowSort();
			// top row should have 0 leading 1s
			// divide whole row by the leading value
			if (ans.matrix[i][j] != 1) {
				double leadValue = ans.matrix[i][j];
				for (int j2 = j; j2 < ans.cols; j2++) {
					ans.matrix[i][j2] = ans.matrix[i][j2] / leadValue;
				}
			}
			//reduce all other entries in same column to 0
			for (int i2 = 0; i2 < ans.rows; i2++) {
				if (i2 == i || ans.matrix[i2][j] == 0) {
					continue;
				}
				double[] n = ans.matrix[i2];
				double[] m = ans.matrix[i];
				double z = -ans.matrix[i2][j];
				ans.matrix[i2] = rowAdd(n, z, m);
			}
		}
		return ans;
	}

	/**
	 * Does same thing as REF, prints out Matrix ans at end of every loop
	 * TODO: add check for matrices that do not reduce to identity.
	 * @return answer
	 */
	public Matrix REFSteps() {
		Matrix ans = this;
		// # of rows with leading 1
		int a = 0;
		int j = 0;
		// loops through rows
		for (int i = 0; i < ans.rows && j < ans.cols - 1; i++, j++) {
			i = a;
			// skip row if entry is 0
			if (ans.matrix[i][j] == 0) {
				continue;
			}
			// move this row to the top
			double[] temp = ans.matrix[i];
			ans.matrix[i] = ans.matrix[a];
			ans.matrix[a] = temp;
			// divide everything if the first entry isn't 1;
			double wtf = temp[j];
			if (ans.matrix[a][j] != 1) {
				for (int j2 = j; j2 < ans.cols; j2++) {
					double foo = ans.matrix[a][j2] / wtf;
					ans.matrix[a][j2] = foo;
				}
			}
			// get all other entries to 0
			for (int i2 = 0; i2 < ans.matrix.length; i2++) {
				if (ans.matrix[i2][j] == 0 || i2 == a) {
					continue;
				}
				ans.matrix[i2] = rowAdd(ans.matrix[i2], -ans.matrix[i2][j], ans.matrix[a]);
			}
			a++;
			ans.printMatrix();
			System.out.println();
		}
		return ans;
	}

	public static void main(String[] args) {
		double[][] value = new double[3][3];
		value[0] = new double[] { 2, 4, 5, 1 };
		value[1] = new double[] { 2, 4, 4, 3 };
		value[2] = new double[] { 2, 4, 3, 10 };
		Matrix a = new Matrix(value);
		a.RREF().printMatrix();
	}
}
