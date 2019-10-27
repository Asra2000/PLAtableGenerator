


import javax.swing.*;
import java.io.*;
import java.util.*;
class Scratch {
    int n,m;String l[];String d[];int g;
    //////////////////////////////////////////////////////////////////
    Scratch(int p,int b)
    {
        n=p;
        m=b;g=0;
        d=new String[(int)Math.pow(2,m)];
        l=new String[(int)Math.pow(4,m)];
    }
    //////////////////////////////////////////////////////////////////
    //---giving values to the global arrays
    void insert(int a[],int b)
    {g=b;
        for(int i=0;i<b;i++)
        {
            l[i]=""+a[i];
            d[i]=l[i];
        }

    }
    ////////////////////////////////////////////////////////////////////
    //---execution starts here
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);String z="";
        System.out.print("Enter the no. of functions:");
        int n=sc.nextInt();
        System.out.print("Enter the no. of literals:");
        int m=sc.nextInt();
        Scratch ob=new Scratch(n,m);
        String g[][]=new String[n][(int)Math.pow(2,m)];
        int f[][]=new int[n][(int)Math.pow(2,m)];
        String g1[][]=new String[n][(int)Math.pow(2,m)];
        int f1[][]=new int[n][(int)Math.pow(2,m)];

        int c[]=new int[n];
        sc.useDelimiter("[,\n]");
        for(int i=0;i<n;i++)
        {
            System.out.print("No. of minterms for the function:");
            c[i]=sc.nextInt();
            for(int j=0;j<c[i];j++)
            {
                f[i][j]=sc.nextInt();
                g[i][j]=ob.binary(f[i][j]);
            }

            sc.next();
        }int b=0;int x=0;
        for(int i=0;i<n;i++)
        {b=0;x=0;
            for (int j=0;j<(int)Math.pow(2,m);j++)
            {
                while(f[i][j]!=b && g[i][j]!=null)
                {
                    f1[i][x]=b;b++;
                    g1[i][x]=ob.binary(f1[i][x]);
                    x++;
                }
                if(g[i][j]==null)
                    break;
                b++;
            }
            while(b!=(int)Math.pow(2,m)&& x<(int)Math.pow(2,m))
            {
                f1[i][x]=b;b++;
                g1[i][x]=ob.binary(f1[i][x]);
                x++;
            }

        }

        for(int i=0;i<n;i++) {
            ob.insert(f[i],c[i]);
            g[i] = ob.reduce(g[i], 1, c[i]);
            if(c[i]!=(int)Math.pow(2,m)){
                ob.insert(f1[i],(int)Math.pow(2,m)-c[i]);
                g1[i]=ob.reduce(g1[i], 1, (int)Math.pow(2,m)-c[i]);}


            System.out.println();}
        int y,y1;

        String s[];String d="";String t[][]=new String[n][(int)Math.pow(2,m)];
        if(n>1) {
            y=ob.maximum(g[0],g,g1,0);
            y1=ob.maximum(g1[0],g,g1,0);
            if(y>=y1 ){z=z+0+"+";
                s=ob.generate(new String[]{""},g[0]);}
            else{z=z+0+"'+";
                s=ob.generate(new String[]{""},g1[0]);}

            for (int j = 1; j < n; j++) {
                y=ob.maximum(g[j],g,g1,j);
                y1=ob.maximum(g1[j],g,g1,j);
                for(int i=0;i<(int)Math.pow(2,m);i++) {
                    if(y1>y)
                        t[j][i] = g1[j][i];
                    else
                        t[j][i]=g[j][i];
                }

                if(y1>y){
                    z=z+j+"'+";
                    s=ob.generate(s,g1[j]);
                }
                else{
                    z=z+j+"+";
                    s=ob.generate(s,g[j]);
                }
                for(int i=0;i<(int)Math.pow(2,m);i++) {
                    if(y1>y)
                        g1[j][i] = t[j][i];
                    else
                        g[j][i]=t[j][i];
                }
            }
        }
        else{
            int j=0;
            while(g[0][j]!=null)
            {
                j++;
            }
            int i=0;
            while(g1[0][i]!=null)i++;
            if(j > i && i!=0){
                s=g1[0];z=z+0+"'+";}
            else{
                s=g[0];z=z+0+"+";}
        }
        String a="";

        System.out.print("\t\t product term\t");char ch='A';
        for(int i=0;i<m;i++)
        {a=a+ch;
            System.out.print(ch+"\t");ch++;
        }String k="";
        for(int i=0;i<z.length();i++) {
            char ch1 = z.charAt(i);
            if (ch1 != '+')
                k = k + ch1;
            else {
                System.out.print("F"+k + "\t");
                if(k.charAt(k.length()-1)=='\'')
                    d=d+0;
                else
                    d=d+1;
                k = "";
            }
        }
        System.out.println();int l=1;
        if(n==1)
            l=0;
        for(int i=l;i<s.length && s[i]!=null;i++) {
            int u=0;
            for(int j=0;j<s[i].length();j++){
                char q=s[i].charAt(j);
                if(q=='$'){
                    continue;}
                else if(q=='0'){u+=2;
                    System.out.print(a.charAt(j)+"'");}
                else{u+=1;
                    System.out.print(a.charAt(j));}
            }
            for(int j=u;j<=8;j++)
                System.out.print(" ");
            System.out.print("\t"+i+"\t\t\t");
            for(int j=0;j<s[i].length();j++){
                System.out.print(s[i].charAt(j)+"\t");}
            for(int j=0;j<n;j++)
            {
                int p;
                if(d.charAt(j)=='0')
                    p=ob.search(g1[j],s[i]);
                else
                    p=ob.search(g[j],s[i]);
                System.out.print(p+"\t");
            }
            System.out.println();
        }
    }
    //////////////////////////////////////////////////////////////////
    //---function to change decimal to binary
    String binary(int a)
    {
        String s="";int d,c=0;
        if(a!=0) {
            while (a != 1) {
                d = a % 2;
                s = d + s;
                c++;
                a = a / 2;
            }
            if(a==1)
                c++;

            s = 1 + s;
        }
        else {
            s = "0";
            c = 1;
        }
        for(int i=c+1;i<=m;i++)
        {
            s="0"+s;
        }
        return s;
    }
    //////////////////////////////////////////////////////////////////
    //---check for duplicate minterms
    int search(String a[],String s)
    {
        for(int i=0;i<a.length;i++)
        {
            if(a[i]!=null)
                if(a[i].equals(s))
                    return 1;

        }
        return 0;
    }
    /////////////////////////////////////////////////////////////////
    //---to reduce each function at a time
    String[]  reduce(String a[],int h,int p) {
        if(h==1) {
            int b[]=new int[p];
            int f=0, d,x = -1;
            for (int k = 0; k < p; k++) {
                b[k]=0;
            }
            String s1[] = new String[(int) Math.pow(4,m)];
            String s[] = new String[(int) Math.pow(4,m)];
            h = 0;
            for (int i = 0; i < p; i++) {
                for (int j = i + 1; j < p; j++) {
                    f = 0;
                    d = 0;
                    for (int k = 0; k < m; k++) {
                        if (f > 1)
                            break;
                        if (a[i].charAt(k) != a[j].charAt(k)) {
                            f++;
                            d = k;

                        }

                    }

                    if (f == 1) {
                        h = 1;
                        b[i] = b[i] + 1;
                        b[j] += 1;
                        x++;s1[x]=l[i]+" "+l[j];
                        s[x]= a[i].substring(0, d) + "$" + a[i].substring(d + 1);

                        int o=0;
                        for(int l=x;l>=0;l--)
                        {
                            o=0;
                            for(int t=0;t<l;t++)
                            {
                                if(s[t]!=null)
                                    if(s[t].equals(s[l]))
                                        o=1;

                            }
                            if(o==1){
                                s1[l]=null;
                                s[l]=null;x--;}
                        }
                    }


                }
            }
            for (int k = 0; k < p; k++) {
                if (b[k] == 0) {
                    x++;
                    s[x] = a[k];s1[x]=l[k];
                }
            }
            for(int i=0;i<l.length;i++)
            {
                if(s1[x]!=null)
                    l[i]=s1[i];
                else
                    l[i]=null;
            }
            return   reduce(s, h, x + 1);
        }
        else {

            String b[];
            if(p!=1)
                b=regenerate(l,a);
            else
                b=a;

            return b;
        }
    }
    //////////////////////////////////////////////////////////////////
    //---to check if prime implicants' table is empty or not
    int empty(int m[][])
    {
        int p=1;
        for(int i=0;l[i]!=null;i++)
        {
            for(int j=0;j<g;j++){
                if(m[i][j]==1) {
                    p = -1;
                    break;
                }
            }

        }
        return p;
    }
    /////////////////////////////////////////////////////////////////
    //---modification of the prime implicants' table
    String[] regenerate(String l[],String a[])
    {
        String b[]=new String[a.length];
        int m[][]=new int [l.length][g];int p=0;
        for(int i=0;l[i]!=null;i++)
        {p++;
            l[i]=l[i]+" ";
            for(int j=0;j<g;j++) {
                String c="";int x=0;
                for(int k=0;k<l[i].length();k++)
                {
                    char ch=l[i].charAt(k);
                    if(ch!=' ')
                        c=c+ch;
                    else
                    {

                        if(c.equals(d[j]))
                            x=1;
                        c="";
                    }
                }
                if(x==1)
                    m[i][j]=1;
                else {
                    m[i][j]=0;
                }
            }}

        int c=0,h=0;int k=0;

        for(int i=0;i<g;i++)
        {c=h=0;
            for(int j=0;j<=p;j++)
            {
                if(m[j][i]==1){
                    h++;c=j;}
            }
            if(h==1) {

                b[k] = a[c];
                k++;

                for (int j1 = 0; j1 < g; j1++) {
                    if (m[c][j1] == 1) {
                        for (int j2 = 0; j2 <= p; j2++) {
                            m[j2][j1] = 0;
                        }
                    }
                }
            }

        }
        h=empty(m);
        while(h==-1)
        {
            c=0;int f=0;
            for(int i=0;l[i]!=null;i++)
            {
                h=0;
                for(int j=0;j<g;j++){
                    if(m[i][j]==1)
                        h++;
                }

                if(h>c)
                {
                    f=i;c=h;
                }
            }

            b[k]=a[f];
            k++;
            for (int j1 = 0; j1 < g; j1++) {
                if (m[f][j1] == 1) {
                    for (int j2 = 0; j2 <= p; j2++) {
                        m[j2][j1] = 0;
                    }
                }
            }
            h=empty(m);

        }

        return b;
    }
    ////////////////////////////////////////////////////////////////////
    //---to generate a single function out of the two given functions
    String[] generate(String a[], String b[]) {
        int i;String c[]=new String[(int)Math.pow(2,m)];
        for (i = 0; i < a.length; i++) {
            if (a[i]==null)
                break;
            for (int j = 0; j < b.length; j++) {
                if (b[j]==null)
                    continue;
                else if (b[j].equals(a[i])) {
                    b[j] = null;
                }
            }
            c[i] = a[i];
        }
        for (int j = 0; j < b.length; j++) {
            if (b[j] != null) {
                c[i] = b[j];
                i++;
            }
        }
        return c;
    }
    //////////////////////////////////////////////////////////////////////////
    //---function to return the maximum matching reduced terms
    int maximum(String a[],String c[][],String c1[][],int d)
    {
        int r=0;
        for(int k=0;a[k]!=null;k++){
            for(int i=0;i<n;i++)
            {
                if(i==d)
                    continue;
                for(int j=0;j<(int)Math.pow(2,m);j++)
                {
                    if(c[i][j]!=null && c[i][j].equals(a[k]))
                        r++;
                    if(c1[i][j]!=null && c1[i][j].equals(a[k]))
                        r++;
                }

            }}
        return r;
    }
}
//-------------------------------------------------------------------------------------------
//---program terminates here
/*details of some important identifiers
f=array to store the minterms
f1=array to store the remaining minterms
g=array to store the binary representative of f terms
g1=array to store the binary representative of f1 terms
i,j,k=loop variables
l=global array to store the minterms
d=global array to store the string of minterms
n=to store the no. of functions
m=to store the no. of variables
r=array storing the reduced minterms
s=array to store the reduced form of function being returned
All other varibles local and are used as per the need of the function
 */







