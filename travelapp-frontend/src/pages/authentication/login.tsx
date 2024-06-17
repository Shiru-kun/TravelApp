import React from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate()

  const handleLogin = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
  };
const signup = ()=>{
  navigate("/signup")
}
const openWithoutLogin = ()=>{
  navigate("/")
}
  return (
    <Layout>
  <div className={styles.container}>
      <img src={vm_logo} alt="Vodafone Logo" className={styles.logo} />
      <h1 className={styles.title}>Login Travel Assistant</h1>
      <form className={styles.form}>
        <input type="email" className={styles.input} placeholder="user@mail.com" required />
        <input type="password" className={styles.input} placeholder="*******" required />

        <button onClick={handleLogin} className={styles.button}>Login</button>
      </form>
      <a onClick={signup} style={{margin:10}} className={styles.buttonSimple}>Signup now</a>
      <a onClick={openWithoutLogin} style={{margin:10}} className={styles.buttonSimple}>Go as guest</a>


      <select className={styles.languageSelector}>
        <option value="pt">Português</option>
        <option value="en">English</option>
        <option value="es">Español</option>
      </select>

      <footer className={styles.footer}>
        © {new Date().getFullYear()} <br />
      </footer>
    </div>
  </Layout>
  
  );
};

export default Login;
