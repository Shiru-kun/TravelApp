import React from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';

const Login = () => {
  const handleLogin = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    // Lógica de login aqui
  };

  return (
    <div className={styles.container}>
      <img src={vm_logo} alt="Vodafone Logo" className={styles.logo} />
      <h1 className={styles.title}>Login Travel Assistant</h1>

      <form className={styles.form}>
        <input type="email" className={styles.input} placeholder="user@mail.com" required />
        <input type="password" className={styles.input} placeholder="*******" required />

        <button onClick={handleLogin} className={styles.button}>Login</button>
      </form>

      <select className={styles.languageSelector}>
        <option value="pt">Português</option>
        <option value="en">English</option>
        <option value="es">Español</option>
      </select>

      <footer className={styles.footer}>
        © {new Date().getFullYear()} <br />
      </footer>
    </div>
  );
};

export default Login;
