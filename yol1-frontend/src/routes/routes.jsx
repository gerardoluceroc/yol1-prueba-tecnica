import { Route, Routes } from "react-router-dom";
import LoginPage from "../pages/LoginPage";

export const AppRoutes = () => (
  <Routes>
    <Route 
        path={"/"} 
        element={
            <LoginPage/>
        } />
  </Routes>
);