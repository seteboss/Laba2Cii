package serviceTest;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import service.ART1;

public class ART1Test {

		boolean checkArr(Integer[] arr, int n, int item){
				for (int i = 0; i < n; i++) {
						if (arr[i] != item){
								return false;
						}
				}
				return true;
		}

		boolean checkZeroMatrix(Integer[][] matrix, int n, int m){
				for (int i = 0; i < n; i++) {
						for (int j = 0; j < m; j++) {
								if (matrix[i][j] != 0){
										return false;
								}
						}
				}
				return true;
		}

		boolean checkData(Integer[][] matrix){
				for (int i = 0; i < 10; i++) {
						for (Integer item: matrix[i]) {
								if (item != 0 && item != 1)
										return false;
						}
				}
				return true;
		}

		@Test
		public void createART1() {

				ART1 art1 = new ART1();
				Assertions.assertNotNull(art1);
		}

		@Test
		public void shouldInit(){
				ART1 art1 = new ART1();
				art1.setN(2);
				art1.setD(2);
				art1.initPrototypeVectors();
				Assertions.assertNotNull(art1.getPrototypeVectors());
				Assertions.assertNotNull(art1.getCountItemInCluster());
				Assertions.assertTrue(checkZeroMatrix(art1.getPrototypeVectors(),
						art1.getN(), art1.getD()));
				Assertions.assertTrue(checkArr(art1.getCountItemInCluster(), art1.getN(), 0));
				Assertions.assertNotNull(art1.getNumberClusterForSignsVectors());
				Assertions.assertTrue(checkArr(art1.getNumberClusterForSignsVectors(), art1.getD(), -1));
		}

		@Test
		public void shouldVectorMagnitude(){
				ART1 art1 = new ART1();
				Integer[] vector1 = {1,0,0,1,0,1,1};
				Assertions.assertEquals(4, art1.vectorMagnitude(vector1));
		}

		@Test
		public void shouldVectorBitwiseAnd(){
				ART1 art1 = new ART1();
				Integer[] vector1 = {1,1,1,0,1,0};
				Integer[] vector2 = {1,1,0,0,1,1};
				Integer[] result = {1,1,0,0,1,0};
				Assertions.assertArrayEquals(result, art1.vectorBitwiseAnd(vector1, vector2, 6));
		}

		@Test
		public void shouldCreateNewPrototypeVector(){
				ART1 art1 = new ART1();
				Integer[] countItemInCluster = {1, 2, 0, 0};
				Integer[] vector = {1, 1, 0, 0};
				art1.setN(4);
				art1.setD(4);
				art1.initPrototypeVectors();
				art1.setCountItemInCluster(countItemInCluster);
				int result = art1.createNewPrototypeVector(vector);
				Assertions.assertArrayEquals(vector, art1.getPrototypeVectors()[2]);
				Assertions.assertEquals(1, art1.getNumberClusterForSignsVectors()[2]);
				Assertions.assertEquals(2, result);
		}

		@Test
		public void shouldInitSignsVector(){
				ART1 art1 = new ART1();
				art1.setD(11);
				art1.initSignsVector();
				Assertions.assertNotNull(art1.getSignsVectors());
				Assertions.assertTrue(checkData(art1.getSignsVectors()));
		}

		@Test
		public void shouldUpdatePrototypeVectorsForOnlyFirst(){
				ART1 art1 = new ART1();
				art1.setD(5);
				art1.setN(5);
				art1.initPrototypeVectors();
				art1.initSignsVector();
				art1.getNumberClusterForSignsVectors()[2] = 2;
				Integer[] expected = Arrays.copyOf(art1.getSignsVectors()[2], art1.getD());
				art1.updatePrototypeVectors(2);
				Assertions.assertArrayEquals(expected, art1.getPrototypeVectors()[2]);
		}

		@Test
		public void shouldUpdatePrototypeVectors(){
				ART1 art1 = new ART1();
				art1.setD(5);
				art1.setN(5);
				art1.initPrototypeVectors();
				art1.initSignsVector();
				art1.getNumberClusterForSignsVectors()[2] = 2;
				art1.getNumberClusterForSignsVectors()[3] = 2;
				Integer[] expected = Arrays.copyOf(art1.getSignsVectors()[2], art1.getD());
				for (int i = 0; i < 5; i++) {
						expected[i] = expected[i] & art1.getSignsVectors()[3][i];
				}
				art1.updatePrototypeVectors(2);
				Assertions.assertArrayEquals(expected, art1.getPrototypeVectors()[2]);
		}

}
