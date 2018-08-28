<h2>тестовое задание</h2>
<p>Необходимо написать мини приложение, которое будет отображать список
мест оказания услуг.</p>
<p>Сам список необходимо получить используя REST сервис и функцию
GetPlaces.</p>
<p>Сам REST серсис доступен по адресу: http://sms.servio...</p>
<p>Описание сервиса: https://drive.google.com/file/...</p>

<h2>решение</h2>
<ul>
<li>сущности представленные в JSON-ответе сервиса Servio описаны в виде отдельных классов в пакете "model";
<li>метод получения данных описан в интерфесе, с использованием библиотеки Retrofit2 в пакете "api";
<li>в том же интерфейсе описан класс "Factory", реализующий фабрику вызовов;
<li>загрузчик данных реализован отдельным классом "DataLoader" реализующим шаблоны "Singleton" и "Observer";
<li>для отображения полученных данных используется виджет RecyclerView, его адаптер реализован отдельным классом "PlaceAdapter";
</ul>

подписанная сборка APK размещена в этом репозитории, в https://github.com/VitalyVasilchuk/ServioTest/tree/master/app/release
