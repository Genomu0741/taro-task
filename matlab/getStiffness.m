function stiffness = getStiffness(para)
E=para(1);
A=para(2);
L=para(3);
I=para(4);
eaDl=E*A/L;
eiDl3=12*E*I/(L^3);
eiDl2=6*E*I/(L^2);
eiDl=E*I/L;
preset=[eaDl eiDl3 4*eiDl eaDl eiDl3 4*eiDl;
    0 eiDl2 0 0 -eiDl2 0;
    0 0 -eiDl2 0 0 0;
    -eaDl -eiDl3 2*eiDl 0 0 0;
    0 eiDl2 0 0 0 0];
st=zeros(6);
for i = 1:5
   st=st+diag(preset(i,1:7-i),i-1);
   st=st+diag(preset(i,1:7-i),1-i); 
end
stiffness=st-diag(preset(1,:));
