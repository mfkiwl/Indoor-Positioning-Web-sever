package cn.gxw;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class FindPath {     //�����·��

	
/*  0----1---2
	     |
	     |
	     |
	     |
	3----4---5
	     |
	     |
	     |
	     |
	6----7---8     
	     */
	
	//Floyd�㷨ʵ��ÿ�Զ�������·��	
	/*
	 �����·���㷨��˼��
	 
	 */
	
	
	
	private static int[][] D;//D�������붥������·��Ȩֵ�͵ľ���
    private static int [][] P;//P�����Ӧ��������·����ǰ������
    private static int numOfNodes;  //�ڵ����
    private static int inf=65535;   //�൱��2��16�η���1
    static ArrayList<Integer> list = new ArrayList<Integer>();  //ʹ������洢·��
    
    public static void init(){
        //������ݳ�ʼ��
        D = new int[][]{
        	{ 0, 4, inf, inf, inf, inf, inf, inf, inf},
        	{ 4, 0, 3, inf, 4, inf, inf, inf, inf},
        	{ inf, 3, 0, inf, inf, inf, inf, inf, inf},
        	{ inf, inf, inf, 0, 4, inf, inf, inf, inf},
        	{ inf, 4, inf, 4, 0, 3, inf, 4, inf},
        	{ inf, inf, inf, inf, 3, 0, inf, inf, inf},
        	{ inf, inf, inf, inf, inf, inf, 0, 4, inf},
        	{ inf, inf, inf, inf, 4, inf, 4, 0, 3},
        	{ inf, inf, inf, inf, inf, inf, inf, 3, 0}
        };
        numOfNodes = D[0].length;
        
        P = new int[numOfNodes][numOfNodes];
        for(int i = 0; i < numOfNodes; i++){
            for(int j = 0; j< numOfNodes; j++){
                P[i][j] = j;            //�����ʼ��
            }
        }
    }
    
    public static void floydMethod(){

        for(int i = 0; i < numOfNodes; i++){
            for(int j = 0; j < numOfNodes; j++) {
                for(int k = 0; k < numOfNodes; k++){
                    if(D[j][k] > D[j][i] + D[i][k]){
                        //���0->1->2 < 0->2
                        D[j][k] = D[j][i] + D[i][k];//�������·��
                        P[j][k] = P[j][i];          //����ǰ��
                    }
                }
            }
        }
    }

    public static void printP(){
        for(int i = 0; i < numOfNodes; i++){
            for(int j = 0; j < numOfNodes; j++){
                System.out.print(P[i][j]+" ");
            }
            System.out.println("");
        }
    }

	public static void printResult(int i, int j) {
		System.out.print(i + "->" + j + " weight: " + D[i][j] + " Path: " + i);// i->j�����·��
		int k = P[i][j]; // �����ǵ�ǰ���ڵ�
		list.add(i);
		while (k != j) { // һֱ��������ӡ
			System.out.print("->" + k + " ");
			list.add(k);
			k = P[k][j]; // ��j,k�������ڵ�
		}
		list.add(j);
		System.out.println(list);
		System.out.print("->" + j + "\n"); // ��ӡPath�ϵ����һ���ڵ�j
	}

    public static void main(String[] args){

        init();
        floydMethod();
        System.out.println(list);
    }
    public static ArrayList<Integer> getList(Integer X,Integer Y)
    {
    	init();
        floydMethod();
        printResult(X,Y);
        System.out.println(list);
		return list;
    }
}
	     


/*{ 0, 4, inf, inf, inf, inf, inf, inf, inf},
{ 4, 0, 3, inf, 4, inf, inf, inf, inf},
{ inf, 3, 0, inf, inf, inf, inf, inf, inf},
{ inf, inf, inf, 0, 4, inf, inf, inf, inf},
{ inf, 4, inf, 4, 0, 3, inf, 4, inf},
{ inf, inf, inf, inf, 3, 0, inf, inf, inf},
{ inf, inf, inf, inf, inf, inf, 0, 4, inf},
{ inf, inf, inf, inf, 4, inf, 4, 0, 3},
{ inf, inf, inf, inf, inf, inf, inf, 3, 0}*/
