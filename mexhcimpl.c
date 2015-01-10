#include "mex.h"
#include "matrix.h"
#include "math.h"

void mexFunction(int nlhs, mxArray *plhs[], int nrhs, const mxArray *prhs[])
{
    unsigned char *edgeim;          
    int rmin,rmax;
    const int  *dimensioni;
    int rows,cols;
    int nradii;
    int dimensioni3d[3];
    double *p;
    int hr,hc,hrhc;
    double weigth = 1.0;
    int *xd,*yd,*px,*py;
    int *x,*y,*changed;
    int Lx,Ly,Lxd,Lyd;
    int cx,cy;
    int radius;
    double fractpart, intpart,prodotto;    
    int ii,jj,cont,index,posjj,posii,n,pos,Lmax,vx,vy;
    
    edgeim     = mxGetData(prhs[0]);
    rmin       = mxGetScalar(prhs[1]);
    rmax       = mxGetScalar(prhs[2]);    
    dimensioni = mxGetDimensions(prhs[0]);
    rows       = dimensioni[0];
    cols       = dimensioni[1];
    nradii     = rmax-rmin+1;
    
    dimensioni3d[0] = rows;
    dimensioni3d[1] = cols;
    dimensioni3d[2] = nradii;
    plhs[0]         = mxCreateNumericArray(3,dimensioni3d,mxDOUBLE_CLASS,mxREAL);
    p               = mxGetData(plhs[0]);
    
    hr   = rows;
    hc   = cols;
    hrhc = hr*hc;
    
    x = mxCalloc(hrhc,sizeof(int));
    y = mxCalloc(hrhc,sizeof(int));
    
    cont = 0;
    for (jj=0;jj<hc;jj++)
    {
        posjj = jj*hr;        
        for (ii=0;ii<hr;ii++)
        {
            posii = posjj+ii;
            if (*(edgeim+posii)>0)
            {
                x[cont]=jj;
                y[cont]=ii;
                cont++;
            }            
        }               
    }
    Lx = cont;
    Ly = cont;
    
    Lmax = (int)(floor((nradii+rmin)/sqrt(2))+1)+1;
    
    changed  = mxCalloc(hrhc,sizeof(int));    
    xd       = mxCalloc(Lmax,sizeof(int));
    yd       = mxCalloc(Lmax,sizeof(int));    
    px       = mxCalloc(8*(Lmax),sizeof(int));
    py       = mxCalloc(8*(Lmax),sizeof(int));    
    for (index=0;index<Ly;index++)
    {
        cx = x[index];
        cy = y[index];
        for (n=1;n<=nradii;n++)
        {
            radius   = n+rmin;
            Lxd      = (int)(((double)radius/sqrt(2))+1);
            Lyd      = Lxd; 
            for (ii=0;ii<Lxd;ii++)
            {
                xd[ii]         = ii;
                prodotto       = (double)radius*sqrt(1-((double)(ii*ii))/((double)(radius*radius)));
                
                
                if ((prodotto-(int)prodotto)>=0.5)
                {
                    yd[ii] = (int)(prodotto)+1;
                }
                else
                {
                    yd[ii] = (int)(prodotto);
                }
            }            
            for (ii=0;ii<Lxd;ii++)
            {
                vx = xd[ii];
                vy = yd[ii];
                
                px[ii] = cy+vx;
                py[ii] = cx+vy;
                
                px[ii+Lxd]   = cy+vy;
                py[ii+Lxd]   = cx+vx;
                
                px[ii+2*Lxd] = cy+vy;
                py[ii+2*Lxd] = cx-vx;
                
                px[ii+3*Lxd] = cy+vx;
                py[ii+3*Lxd] = cx-vy;
                
                px[ii+4*Lxd] = cy-vx;
                py[ii+4*Lxd] = cx-vy;
                
                px[ii+5*Lxd] = cy-vy;
                py[ii+5*Lxd] = cx-vx;
                
                px[ii+6*Lxd] = cy-vy;
                py[ii+6*Lxd] = cx+vx;
                
                px[ii+7*Lxd] = cy-vx;
                py[ii+7*Lxd] = cx+vy;                            
            }            
            for (ii=0;ii<hrhc;ii++)
            {
                changed[ii] = 0;  
            }
            for (ii=0;ii<Lxd*8;ii++)
            {
                if ((px[ii]>=0) && (px[ii]<hr) && (py[ii]>=0) && (py[ii]<hc))
                {
                    pos = px[ii]+hr*py[ii];
                    if (changed[pos]==0)
                    {
                        p[(n-1)*hrhc+pos] += weigth;
                        changed[pos]       = 1;   
                    }
                }
            }                       
        }              
    }    
    mxFree(xd);
    mxFree(yd);
    mxFree(px);
    mxFree(py);
    mxFree(x);
    mxFree(y);
    mxFree(changed);
}
