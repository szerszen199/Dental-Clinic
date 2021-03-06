import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {HashRouter as Router} from 'react-router-dom';
import ErrorBoundary from "./components/ErrorBoundary/ErrorBoundary";
import {I18nextProvider} from "react-i18next";
import i18n from "./transaltions/i18n";

ReactDOM.render(
    <React.StrictMode>
        <Router>
            <I18nextProvider i18n={i18n}>
                <ErrorBoundary>
                    <App/>
                </ErrorBoundary>
            </I18nextProvider>
        </Router>
    </React.StrictMode>,
    document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
