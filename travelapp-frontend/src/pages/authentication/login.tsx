import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { AUTH_LOCALSTORAGE, LANGUAGES, LOGIN } from '../../utils/constants';
import toast, { Toaster } from 'react-hot-toast';
import { useTranslation } from 'react-i18next';

const Login = () => {

  const navigate = useNavigate()
  const { i18n, t } = useTranslation();
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');

  const handleLogin = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    try {
      const loginResponse: any = await post(LOGIN, {
        email, password
      })
      if (loginResponse === undefined || loginResponse === null) {
        toast.error(t("BadCredentials"));
        return;
      }
      localStorage.setItem(AUTH_LOCALSTORAGE, JSON.stringify(loginResponse))
      navigate("/")
    } catch (ex: any) {
      if (ex?.response?.data?.message) {
        toast.error(`${ex?.response?.data?.message}`)
      }
      console.log({ ex })
    }


  };
  const onChangeLang = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const lang_code = e.target.value;
    i18n.changeLanguage(lang_code);
  };
  const signup = () => {
    navigate("/signup")
  }
  const openWithoutLogin = () => {
    navigate("/")
  }
  return (
    <Layout>
      <div><Toaster /></div>
      <div className={styles.container}>
        <img src={vm_logo} loading="lazy" alt="Vodafone Logo" className={styles.logo} />
        <h1 className={styles.title}>{t("LoginTravelAssistant")}</h1>
        <form className={styles.form}>
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            className={styles.input}
            placeholder="user@mail.com"
            required
          />
          <input
            type="password"
            className={styles.input}
            placeholder="*******"
            value={password}
            onChange={e => setPassword(e.target.value)}
            required />

          <button onClick={handleLogin} className={styles.button}>{t("Login")}</button>
        </form>
        <span onClick={signup} style={{ margin: 10 }} className={styles.buttonSimple}>{t("SignupNow")} </span>
        <span onClick={openWithoutLogin} style={{ margin: 10 }} className={styles.buttonSimple}>{t("GoAsGuest")}</span>


        <select className={styles.languageSelector} defaultValue={i18n.language} onChange={onChangeLang}>
          {LANGUAGES.map(({ code, label }) => (
            <option key={code} value={code}>
              {label}
            </option>
          ))}
        </select>

        <footer className={styles.footer}>
          {new Date().getFullYear()} <br />
        </footer>
      </div>
    </Layout>

  );
};

export default Login;
