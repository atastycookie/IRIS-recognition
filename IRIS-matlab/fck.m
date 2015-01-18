function fck

%open file
fid=fopen('iris_database.dat');
%go to beginning of the file
fseek(fid,0,-1);
data=[];
while feof(fid)==0 %do it for the complete file
    thisline=fgetl(fid); % get the line of text
    temp=textscan(thisline, '%s %s %s %s %s'); 
    % reads for example 4 columns of  floating point numbers
    if ~isempty(temp{1})
        data=[data; temp];
    end
end
fclose(fid);