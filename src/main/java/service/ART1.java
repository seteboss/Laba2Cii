package service;

import java.util.Arrays;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ART1 {

		private Random random = new Random();

		private int N; //MAX count prototype vectors

		private double p;

		private int d; //MAX count item in vector

		private double beta;

		private int numPrototypeVectors = 0;

		private Integer[][] prototypeVectors;

		private Integer[][] signsVectors;

		private Integer[] countItemInCluster;

		private Integer[] numberClusterForSignsVectors;


		public void initSignsVector(){
				signsVectors = new Integer[10][d];
				for (int i = 0; i < 10; i++) {
						for (int j = 0; j < d; j++) {
								signsVectors[i][j] = random.nextInt(2);
						}
				}
		}


		public void initPrototypeVectors() {
				prototypeVectors = new Integer[N][d];
				countItemInCluster = new Integer[N];
				for (int i = 0; i < N; i++) {
						Arrays.fill(prototypeVectors[i], 0);
						countItemInCluster[i] = 0;
				}
				numberClusterForSignsVectors = new Integer[10];
				Arrays.fill(numberClusterForSignsVectors, -1);
		}


		public int vectorMagnitude(Integer[] vector) {
				int count = 0;
				for (Integer item : vector) {
						if (item == 1) {
								count++;
						}
				}
				return count;
		}


		public Integer[] vectorBitwiseAnd(Integer[] vector1, Integer[] vector2, int n) {
				Integer[] result = new Integer[n];
				for (int i = 0; i < n; i++) {
						result[i] = vector1[i] & vector2[i];
				}
				return result;
		}

		public int[] getClusterCof(){
				int[] clusterCof = new int[N];
				int count = 0;
				for (int i = 0; i < N; i++) {
						clusterCof[i] = count;
						count += countItemInCluster[i];
				}
				return clusterCof;
		}

		public int createNewPrototypeVector(Integer[] vector) {
				int cluster = 0;
				for (Integer item : countItemInCluster) {
						if (item == 0) {
								break;
						}
						cluster++;
				}
				if (cluster == N) {
						throw new RuntimeException("Превышено количество кластеров");
				}
				numPrototypeVectors++;
				for (int i = 0; i < d; i++) {
						prototypeVectors[cluster][i] = vector[i];
				}
				countItemInCluster[cluster] = 1;
				return cluster;
		}


		public void updatePrototypeVectors(int cluster) {
				boolean first = true;
				if (cluster < 0) {
						throw new RuntimeException();
				}
				Arrays.fill(prototypeVectors[cluster], 0);
				for (int i = 0; i < 10; i++) {
						if (numberClusterForSignsVectors[i] == cluster) {
								if (first) {
										for (int j = 0; j < d; j++) {
												prototypeVectors[cluster][j] = signsVectors[i][j];
										}
										first = false;
								} else {
										for (int j = 0; j < d; j++) {
												prototypeVectors[cluster][j] =
														prototypeVectors[cluster][j] & signsVectors[i][j];
										}
								}
						}
				}
		}

		public void perform() {
				int count = 50;
				boolean done = false;
				while (!done) {
						done = true;
						for (int index = 0; index < 10; index++) {
								for (int pvec = 0; pvec < N; pvec++) {
										if (countItemInCluster[pvec] != 0) {
												Integer[] andResult = vectorBitwiseAnd(signsVectors[index],
														prototypeVectors[pvec], d);
												double magPE = vectorMagnitude(andResult);
												double magP = vectorMagnitude(prototypeVectors[pvec]);
												double magE = vectorMagnitude(signsVectors[index]);
												double result = magPE / (beta + (double) magP);
												double test = magE / (beta + (double) d);
												if (result > test) {
														if ((magPE / magE) < p) {
																int old;
																if (numberClusterForSignsVectors[index] != pvec) {
																		old = numberClusterForSignsVectors[index];
																		numberClusterForSignsVectors[index] = pvec;
																		if (old >= 0) {
																				countItemInCluster[old]--;
																				if (countItemInCluster[old] == 0) {
																						numPrototypeVectors--;
																				}
																		}
																		countItemInCluster[pvec]++;
																		if ((old >= 0) && (old < N)) {
																				updatePrototypeVectors(old);
																		}
																		updatePrototypeVectors(pvec);
																		done = false;
																		break;
																}
														}
												}
										}
								}
								if (numberClusterForSignsVectors[index] == -1) {
										numberClusterForSignsVectors[index] = createNewPrototypeVector(
												signsVectors[index]);
										done = false;
								}
						}
						if (count-- != 0) {
								break;
						}
				}
		}
}
