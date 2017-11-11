<%-- 
    Document   : main
    Created on : 06.11.2017, 19:54:19
    Author     : Dmitriy Malakhov
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="app">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="static/style.css">
        <script src="https://code.angularjs.org/1.5.8/angular.min.js"></script>
        <script src="https://code.angularjs.org/1.5.8/angular-animate.min.js"></script>
        <script src="https://code.angularjs.org/1.5.8/angular-aria.min.js"></script>
        <script src="https://code.angularjs.org/1.5.8/angular-loader.min.js"></script>
        <script>
            angular.module('app', []).controller('MainCtrl', 
                function($scope, $http, $interval) {
                    $scope.downloaders = {
                    <c:forEach items="${downloaders}" var="downloader">
                        "${downloader.id}": "${downloader.name}",        
                    </c:forEach>
                    };   
                    $scope.activeSessions = [];
                    $scope.sessions = [];
                    $scope.errors = [];
                    $scope.showErrors = {};
                    $scope.update = function (){
                        $http.get("update")
                            .then(function(response) {
                                $scope.activeSessions = response.data.activeSession;
                                $scope.sessions = response.data.sessions;
                                $scope.errors = response.data.errors;
                            });
                    }
                    $scope.update();
                    $interval($scope.update, 10000);
                    $scope.activeTime = function(date){
                        return new Date().getTime() - new Date(date).getTime();
                    }    
                    $scope.runDownloader = function(id){
                        $http.get("run?id=" + id).then(function() {});
                    };
                }
             );
        </script>
    </head>
    <body ng-controller="MainCtrl">
        <div class="header">
            STAGIRS CRAWLER: МОНИТОРИНГ НАУЧНЫХ ТЕКСТОВ
        </div>
        <div class="modules" style="background-color: #fff">
            <div class="title">
                Доступные модули
            </div>
            <div class="downloader" ng-repeat="(id, name) in downloaders">
                <div class="inline-block" style="width: 400px;color: #777;">
                    {{name}}
                </div>
                <div class="btn block inline-block" ng-click="runDownloader(id)">
                    <i class="fa fa-play-circle" aria-hidden="true"></i>
                    <div class="inline-block" style="overflow: hidden;text-overflow: ellipsis;width: 130px;text-align: center">
                        {{id}}
                    </div>    
                </div>
            </div>
        </div>
        <div class="modules" style="background-color: #fff"  ng-if="activeSessions.length > 0 || sessions.length > 0">
            <div ng-if="activeSessions.length > 0">
                <div class="title">
                    Текущие загрузки
                </div>
                <table style="margin-top:10px; margin-bottom: 10px;border-collapse: collapse;width: 580px;text-align: center" >
                    <tr>
                        <th>Начало</th>
                        <th>Модуль</th>
                        <th>Время (сек.)</th>
                        <th>Скачано</th>
                        <th>Повторов</th>
                        <th>Ошибок</th>
                    </tr>
                    <tr ng-class="{error: session.fatalError}" ng-repeat="session in activeSessions">
                        <td>{{session.dateTime}}</td>
                        <td>{{session.downloaderId}}</td>
                        <td>{{(activeTime(session.dateTime) / 1000).toFixed(0)}}</td>
                        <td>{{session.downloadCount}}</td>
                        <td>{{session.duplicateCount}}</td>
                        <td>{{session.errorCount}}</td>
                    </tr>
                </table>
            </div>
            <div ng-if="sessions.length > 0">
                <div class="title">
                    Прошлые загрузки
                </div>
                <table style="margin-top:10px; margin-bottom: 10px;border-collapse: collapse;width: 580px;text-align: left" >
                    <tr>
                        <th style='width: 145px'>Начало</th>
                        <th>Модуль</th>
                        <th>Cек.</th>
                        <th>Скачано</th>
                        <th>Повторов</th>
                        <th>Ошибок</th>
                    </tr>
                    <tr ng-class="{error: session.fatalError}" ng-repeat="session in sessions">
                        <td style='width: 145px'>{{session.dateTime}}</td>
                        <td>{{session.downloaderId}}</td>
                        <td>{{(session.time / 1000).toFixed(0)}}</td>
                        <td>{{session.downloadCount}}</td>
                        <td>{{session.duplicateCount}}</td>
                        <td>{{session.errorCount}}</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="modules" style="background-color: #fff" ng-if="errors.length > 0">
            <div class="title">
                Ошибки
            </div>
            <table style="margin-top:10px; margin-bottom: 10px;border-collapse: collapse;width: 580px;text-align: left" >
                <tr>
                    <th style='width: 145px'>Дата и время</th>
                    <th>Модуль</th>
                    <th>Сообщение</th>
                </tr>
                <tr ng-repeat="(i, error) in errors" style='vertical-align: top;'>
                    <td style='width: 145px'>{{error.dateTime}}</td>
                    <td>{{error.downloaderId}}</td>
                    <td style='width:300px'>
                        <i class='fa fa-eye' ng-click='showErrors[i] = true' ng-show='!showErrors[i]'></i>
                        <i class='fa fa-eye-slash' ng-click='showErrors[i] = false' ng-show='showErrors[i]'></i>
                        {{error.message}} 
                        <div style='width:300px;overflow-x: auto' ng-show='showErrors[i]'>
                            <div style='font-weight: bold; padding: 10px'>Stack Trace</div>
                            <div ng-repeat="stackItem in error.exeption.split(' ')">
                                {{stackItem}}
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
