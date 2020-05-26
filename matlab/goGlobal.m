function globalStiffness=goGlobal(stiffness,angle)
    T=assembled(getRot(angle/360*2*pi));
    globalStiffness=T'*stiffness*T;
    function aMatrix=assembled(mat)
        n=size(mat,1);
        aMatrix=[mat,zeros(n,n);zeros(n),mat];
    end
    function rotation=getRot(theta)
        rotation=diag([cos(theta) cos(theta) 1])+diag([sin(theta) 0],1)+diag([-sin(theta) 0],-1);
    end
end