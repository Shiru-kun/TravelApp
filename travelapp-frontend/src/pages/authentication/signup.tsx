import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { SIGN_UP } from '../../utils/constants';
import toast, { Toaster } from 'react-hot-toast';

export default function Signup() {
  const navigate = useNavigate()
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [fullname, setFullname] = useState<string>('');

  const login = () => {
    navigate("/login")
  }
  const handleSignup =async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>)=> {
    e.preventDefault();
    try{
      const accountResponse:any = await post(SIGN_UP, {
        email,password,fullname
      });
      if(accountResponse===undefined || accountResponse===null ){
        toast.error(`Can't register user`);
        return;
     }
     if(accountResponse?.id){
       toast.success("User registered, you can sign now");
       login();
     }
    }catch(ex:any){
      if(ex?.response?.data?.message){
        toast.error(`${ex?.response?.data?.message}`)
      }
      console.log({ex});
    }

  }

  return (
    <Layout>
      <div><Toaster/></div>
      <div className={styles.container}>
        <img src={vm_logo} loading="lazy" alt="Vodafone Logo" className={styles.logo} />
        <h1 className={styles.title}>Signup Travel Assistant</h1>

        <form className={styles.form}>
          <input
            type="text"
            className={styles.input}
            placeholder="Your name"
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

          <button onClick={handleSignup} className={styles.button}>Signup</button>
        </form>
        <a onClick={login} style={{ margin: 10 }} className={styles.buttonSimple}>Login</a>


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