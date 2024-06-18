import { render, screen } from "@testing-library/react";
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import Signup from "../../pages/authentication/signup";

const mockNavigate = jest.fn();
jest.mock('react-router',()=>({
    ...jest.requireActual('react-router'),
    useNavigate:()=>mockNavigate
}
));
const MockSignup = () => {
    const queryClient = new QueryClient();
    return (<QueryClientProvider client={queryClient}>
        <BrowserRouter>
            <Signup />
        </BrowserRouter>
    </QueryClientProvider>);
};
describe("Lets test Signup",()=>{
  it("it should render Signup page",()=>{
    render(
        <MockSignup/>
    );
expect(screen.getByText("SignupTravelAssistant")).toBeInTheDocument()
   
  })

})