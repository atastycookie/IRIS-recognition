%
%
% Unzip all files in Matlab current directory and
% type "irisrecognition" on Matlab command window.
%
%
% Функции
%
% Сделать фотографию глаза:                       Открывается превью камеры и производится снимок глаза для последующего его использования для аутентификации.
%
% Выбрать файл:                                    
%
% Добавить изображение в базу данных:                the input image is added to database and will be used for training
%
% Информация о базе данных:                                 show informations about the images present in database.
%
% Определение пользователя по радужке:                              iris matching. The selected input image is processed
%
% Удалить базу данных:                            Удаляет все сохраненные фотографии всех пользователей.
%
% Выйти:                                          Закрытие программы.
%
%
% First, select an input image clicking on "Select image".
% Then you can
%   - add this image to database (click on "Add selected image to database"
%   - perform iris recognition (click on "Iris Recognition" button)
%     Note: If you want to perform iris recognition database has to include 
%     at least one image.
%  If you choose to add image to database, a positive integer (iris ID) is
%  required. This posivive integer is a progressive number which identifies
%  a person (each person corresponds to a class).
% For example:
%  - run the GUI (type "irisrecognition" on Matlab command window)
%  - delete database (click on "Delete Database")
%  - add "iris_mike1.jpg" to database ---> the ID has to be 1 since Mike is the first
%    person you are adding to database
%  - add "iris_mike2.jpg" to database ---> the ID has to be 1 since you have already
%    added a Mike's image to database
%  - add "iris_paul1.jpg" to database ---> the ID has to be 2 since Paul is the second person
%    you are adding to database
%  - add "iris_cindy1.jpg" to database ---> the ID has to be 3 since Cindy is
%    the third person you are adding to database
%  - add "iris_paul2.jpg" to database ---> the ID has to be 2 once again since
%    you have already added Paul to database
%  - add "iris_paul3.jpg" to database ---> the ID has to be 2 once again since
%    you have already added Paul to database
%   
% ... and so on! Very simple, isnt't?
%
%