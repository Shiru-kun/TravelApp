import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { LANGUAGES, SIGN_UP } from '../../utils/constants';
import toast, { Toaster } from 'react-hot-toast';
import { useTranslation } from 'react-i18next';

export default function Signup() {
  const navigate = useNavigate()
  const { i18n, t } = useTranslation();

  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [fullname, setFullname] = useState<string>('');

  const login = () => {
    navigate("/login")
  }
  const onChangeLang = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const lang_code = e.target.value;
    i18n.changeLanguage(lang_code);
  };
  const handleSignup = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    try {
      const accountResponse: any = await post(SIGN_UP, {
        email, password, fullname
      });
      if (accountResponse === undefined || accountResponse === null) {
        toast.error(t('CantRegisterUser'));
        return;
      }
      if (accountResponse?.id) {
        toast.success(t('UserRegisteredYouCanSignNow'));
        login();
      }
    } catch (ex: any) {
      if (ex?.response?.data?.message) {
        toast.error(`${ex?.response?.data?.message}`)
      }
      console.log({ ex });
    }

  }

  return (
    <Layout>
      <div><Toaster /></div>
      <div className={styles.container}>
        <img src={vm_logo} loading="lazy" alt="Vodafone Logo" className={styles.logo} />
        <h1 className={styles.title}>{t('SignupTravelAssistant')}</h1>

        <form className={styles.form}>
          <input
            type="text"
            className={styles.input}
            placeholder={t("Yourfullname")}
            value={fullname}
            onChange={e => setFullname(e.target.value)}
            required />

          <input type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            className={styles.input}
            placeholder="user@mail.com"
            required />
          <input
            value={password}
            onChange={e => setPassword(e.target.value)}
            type="password"
            className={styles.input}
            placeholder="*******"
            required />

          <button onClick={handleSignup} className={styles.button}>{t('Signup')}</button>
        </form>
        <a onClick={login} style={{ margin: 10 }} className={styles.buttonSimple}>{t('Login')}</a>


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
}