import React, { useState } from 'react';
import styles from '../../styles/pages/authentication/auth.module.scss';
import vm_logo from '../../assets/vm_logo.png';
import Layout from './layout';
import { useNavigate } from 'react-router-dom';
import { post } from '../../utils/axios-config';
import { AUTH_LOCALSTORAGE, LOGIN } from '../../utils/constants';
import { useAuth } from './Authprovider';
import toast, { Toaster } from 'react-hot-toast';

const Login = () => {
  
  const navigate = useNavigate()
  
  const [email, setEmail] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const {login } = useAuth() || {};

  const handleLogin = async (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
    e.preventDefault();
    try{
      const loginResponse:any = await post(LOGIN, {
        email,password
      })
      if(loginResponse===undefined || loginResponse===null ){
         toast.error(`Bad credentials`);
         return;
      }
      localStorage.setItem(AUTH_LOCALSTORAGE, JSON.stringify(loginResponse))
     
      if(loginResponse?.userAccountDto?.fullname){
         if(login){
           login(loginResponse?.userAccountDto?.fullname);
         }
        }
        navigate("/")

    }catch(ex:any){
      if(ex?.message){
        toast.error(`${ex?.message}`)
      }
      console.log({ex})
    }
   

  };
const signup = ()=>{
  navigate("/signup")
}
const openWithoutLogin = ()=>{
  navigate("/")
}
  return (
    <Layout>
      <div><Toaster/></div>
  <div className={styles.container}>
      <img src={vm_logo} alt="Vodafone Logo" className={styles.logo} />
      <h1 className={styles.title}>Login Travel Assistant</h1>
      <form className={styles.form}>
        <input 
          type="email"
          value={email}
          onChange={e=>setEmail(e.target.value)}
          className={styles.input}
          placeholder="user@mail.com"
          required
           />
        <input 
          type="password"
          className={styles.input}
          placeholder="*******"
          value={password}
          onChange={e=>setPassword(e.target.value)}
          required />

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
