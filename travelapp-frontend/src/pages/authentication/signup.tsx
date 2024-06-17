import React from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';

export default function Signup() {
  function handleSignup(e:React.MouseEvent<HTMLButtonElement, MouseEvent>) {
  }

  return (
    <Layout>
      <div className={styles.container}>
        <img src={vm_logo} alt="Vodafone Logo" className={styles.logo} />
        <h1 className={styles.title}>Signup Travel Assistant</h1>

        <form className={styles.form}>
        <input type="text" className={styles.input} placeholder="Your name" required />

          <input type="email" className={styles.input} placeholder="user@mail.com" required />
          <input type="password" className={styles.input} placeholder="*******" required />

          <button onClick={handleSignup} className={styles.button}>Signup</button>
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
    </Layout> 
  );
  }