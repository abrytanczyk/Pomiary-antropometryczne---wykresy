

col1 = bmim24(:, 1);
col2 = bmim24(:, 2);
col3 = bmim24(:, 3);
col4 = bmim24(:, 4);
col5 = bmim24(:, 5);
col6 = bmim24(:, 6);
col7 = bmim24(:, 7);
col8 = bmim24(:, 8);
c1=table2array(col1);
c2=table2array(col2);
c3=table2array(col3);
c4=table2array(col4);
c5=table2array(col5);
c6=table2array(col6);
c7=table2array(col7);
c8=table2array(col8);
hold on
plot(c1, c2);
plot(c1, c3);
plot(c1, c4);
plot(c1, c5);
plot(c1, c6);
plot(c1, c7);
plot(c1, c8);